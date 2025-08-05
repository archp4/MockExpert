package com.group7.mockexpert.api_helpers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.group7.mockexpert.models.ListeningTest;
import com.group7.mockexpert.models.Passage;
import com.group7.mockexpert.models.ReadingTestResponse;
import com.group7.mockexpert.models.Section;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ListeningService {
    final private ListeningListener listener;
    private WebSocketClient webSocketClient;
    private static final String WS_URL = ApiConst.websocketURL + "/listening";

    public ListeningService(ListeningListener listener) {
        this.listener = listener;
    }

    public void connectAndRequestListeningTest(Context context) {
        try {
            URI uri = new URI(WS_URL);
            webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handShakeData) {
                    Log.d("WebSocket", "Connection opened");
                    try {
                        JSONObject requestObj = new JSONObject();
                        requestObj.put("request", "listening");
                        webSocketClient.send(requestObj.toString());
                    } catch (Exception e) {
                        listener.onError("JSON Error: " + e.getMessage());
                    }
                }

                @Override
                public void onMessage(String message) {
                    Log.d("WebSocket", "Received: " + message);
                    try {
                        List<Passage> passageList = new ArrayList<>();
                        Gson gson = new Gson();
                        ListeningTest test = gson.fromJson(message, ListeningTest.class);
                        List<Section> sectionList = new ArrayList<Section>();
                        sectionList.add(test.listeningTest.section1);
                        sectionList.add(test.listeningTest.section2);
                        sectionList.add(test.listeningTest.section3);
                        sectionList.add(test.listeningTest.section4);
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(() ->
                                    listener.onResponse(sectionList));
                        }

                    } catch (Exception e) {
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(() ->{
                                Log.e("WebSocket", "JSON Parsing Error: " + e.getMessage());
                                listener.onError("JSON Parsing Error: " + e.getMessage());
                            });
                        }
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("WebSocket", "Closed: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    Log.e("WebSocket", "Error: " + ex.getMessage());
                    if (context instanceof Activity){
                        ((Activity) context).runOnUiThread(() -> listener.onError("Error: " + ex.getMessage()));
                    }
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            listener.onError("URI Syntax Error: " + e.getMessage());
        }
    }
}
