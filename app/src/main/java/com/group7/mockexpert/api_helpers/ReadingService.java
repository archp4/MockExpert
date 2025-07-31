package com.group7.mockexpert.api_helpers;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.group7.mockexpert.models.Passage;
import com.group7.mockexpert.models.Question;
import com.group7.mockexpert.models.ReadingTest;
import com.group7.mockexpert.models.ReadingTestResponse;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReadingService {
    final private ReadingListener listener;
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
                    try {
                        JSONObject root = new JSONObject(message);
                        String readingTestStr = root.getString("reading_test");
                        JSONObject readingTest = new JSONObject(readingTestStr);
                        List<Passage> passageList = new ArrayList<>();
                        for (int i = 1; i <= readingTest.length(); i++) {
                            JSONObject passageJson = readingTest.getJSONObject("passage_" + i);
                            String title = passageJson.getString("title");
                            String context = passageJson.getString("context");
                            String text = passageJson.getString("text");
                            JSONArray questionsArray = passageJson.getJSONArray("questions");
                            List<Question> questionList = new ArrayList<>();
                            for (int j = 0; j < questionsArray.length(); j++) {
                                JSONObject q = questionsArray.getJSONObject(j);
                                Question question = new Question();
                                question.setNumber(q.getInt("number"));
                                question.setQuestion(q.getString("question"));
                                question.setAnswer(q.getString("answer"));
                                question.setType(q.getString("type"));
                                if (q.has("word_limit")) {
                                    question.setWord_limit(q.getString("word_limit"));
                                }
                                if (q.has("options")) {
                                    JSONArray optionsArray = q.getJSONArray("options");
                                    List<String> options = new ArrayList<>();
                                    for (int k = 0; k < optionsArray.length(); k++) {
                                        options.add(optionsArray.getString(k));
                                    }
                                    question.setOptions(options);
                                }

                                if (q.has("headings_options")) {
                                    JSONObject headingObj = q.getJSONObject("headings_options");
                                    Map<String, String> headingMap = new HashMap<>();
                                    Iterator<String> keys = headingObj.keys();
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        headingMap.put(key, headingObj.getString(key));
                                    }
                                    question.setHeadings_options(headingMap);
                                }

                                questionList.add(question);
                            }
                            Passage passage = new Passage();
                            passage.setTitle(title);
                            passage.setContext(context);
                            passage.setText(text);
                            passage.setQuestions(questionList);
                            passageList.add(passage);
                        }
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(() ->
                                    listener.onReceive(passageList));
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
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError("Error: " + ex.getMessage());
                            }
                        });
                    }
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            listener.onError("URI Syntax Error: " + e.getMessage());
        }
    }
}
