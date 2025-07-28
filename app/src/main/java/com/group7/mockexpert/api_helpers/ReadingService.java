package com.group7.mockexpert.api_helpers;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.group7.mockexpert.models.ReadingTestResponse;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
import org.json.JSONObject;

public class ReadingService {
    private ReadingListener listener;
    private WebSocketClient webSocketClient;
    private static final String WS_URL = ApiConst.websocketURL + "/reading";

    public ReadingService(ReadingListener listener) {
        this.listener = listener;
    }

    public void connectAndRequestReadingTest(Context context) {
        try {
            URI uri = new URI(WS_URL);

            webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("WebSocket", "Connection opened");

                    // Send reading request
                    try {
                        JSONObject requestObj = new JSONObject();
                        requestObj.put("request", "reading");
                        webSocketClient.send(requestObj.toString());
                    } catch (Exception e) {
                        listener.onError("JSON Error: " + e.getMessage());
                    }
                }

                @Override
                public void onMessage(String message) {
                    Log.d("WebSocket", "Received: " + message);
                    Gson gson = new Gson();
                    ReadingTestResponse response = gson.fromJson(message, ReadingTestResponse.class);
                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onReceive(response);
                            }
                        });
                    }
                    webSocketClient.close();
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("WebSocket", "Closed: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    Log.e("WebSocket", "Error: " + ex.getMessage());
                    listener.onError("WebSocket error: " + ex.getMessage());
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            listener.onError("URI Syntax Error: " + e.getMessage());
        }
    }
}
