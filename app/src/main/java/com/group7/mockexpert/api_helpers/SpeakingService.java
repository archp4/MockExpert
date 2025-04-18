package com.group7.mockexpert.api_helpers;


import android.app.Activity;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.group7.mockexpert.models.SpeakingQuestion;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeakingService {
    SpeakingAPIListener listener;
    SpeakingCompleteListener completeListener;
    String mainURL = "https://mock-expert-api.vercel.app/speaking/";

    public SpeakingService(SpeakingAPIListener listener){
        this.listener = listener;
    }

    public SpeakingService(SpeakingAPIListener listener,SpeakingCompleteListener completeListener){
        this.listener = listener;
        this.completeListener = completeListener;
    }

    public SpeakingService(SpeakingCompleteListener listener){
        this.completeListener = listener;
    }

    public void requestSpeakingPartOne(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = mainURL + "1/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            int questionPart = response.getInt("QuestionPart");
                            JSONArray questionsArray = response.getJSONArray("questions");
                            List<String> questions = new ArrayList<String>();
                            for (int i = 0; i < questionsArray.length(); i++) {
                                String question = questionsArray.getString(i);
                                questions.add(question);
                            }
                            SpeakingQuestion speakingQuestion = new SpeakingQuestion();
                            speakingQuestion.setQuestionPart(questionPart);
                            speakingQuestion.setQuestions(questions);
                            listener.onReceive(speakingQuestion);
                        } catch (Exception e){
                            listener.onError(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.getMessage());
                    }
                }
        );
        request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(10000, com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES, com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public SpeakingQuestion requestSpeakingIntro(){
        List<String> question = new ArrayList<>();
        question.add("May I know your full name");
        question.add("How may i address you");
        question.add("May I see your ID");
//        question.add("Where do you come from");
//        question.add("Are you student or Do you work");
        SpeakingQuestion startQuestion = new SpeakingQuestion();
        startQuestion.setQuestionPart(1);
        startQuestion.setQuestions(question);
        return startQuestion;
    }

    public void requestSpeakingPartTwo(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = mainURL + "2/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            int questionPart = response.getInt("QuestionPart");
                            JSONArray questionsArray = response.getJSONArray("questions");
                            List<String> questions = new ArrayList<String>();
                            for (int i = 0; i < questionsArray.length(); i++) {
                                String question = questionsArray.getString(i);
                                questions.add(question);
                            }
                            SpeakingQuestion speakingQuestion = new SpeakingQuestion();
                            speakingQuestion.setQuestionPart(questionPart);
                            speakingQuestion.setQuestions(questions);
                            listener.onReceive(speakingQuestion);
                        } catch (Exception e){
                            listener.onError(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.getMessage());
                    }
                }
        );
        queue.add(request);
    }

    public void requestUpload(Context context,Map<String,File> audioFiles, List<String> questions){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        VolleyMultipartRequest request = new VolleyMultipartRequest(
                Request.Method.POST,
                "http://10.0.0.102:8000/uploadIntro",
                audioFiles,
                null,
                questions,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d("Upload", "Success: " + new String(response.data));
                        Log.d("Converted List of Questions",parseStringListFromNetworkResponse(response).toString());
                        SpeakingQuestion question = new SpeakingQuestion();
                        question.setQuestions(parseStringListFromNetworkResponse(response));
                        question.setQuestionPart(1);
                        listener.onReceive(question);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Upload", "Error: " + error.getMessage());
                        listener.onError("Error: " + error.getMessage());
                    }
                }
        );
        requestQueue.add(request);
    }


    public void uploadViaWebSocket(Context context, Map<String, File> audioFiles, List<String> questions) {
        try {
            URI uri = new URI("ws://10.0.0.102:8000/ws/uploadIntro");

            WebSocketClient webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    try {
                        // Step 1: Send questions
                        JSONObject questionMessage = new JSONObject();
                        questionMessage.put("type", "question");
                        questionMessage.put("questions", new JSONArray(questions));
                        send(questionMessage.toString());

                        // Step 2: Send audio files one by one
                        for (Map.Entry<String, File> entry : audioFiles.entrySet()) {
                            byte[] bytes = fileToBytes(entry.getValue());
                            String encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);

                            JSONObject audioMessage = new JSONObject();
                            audioMessage.put("type", "audio");
                            audioMessage.put("filename", entry.getKey());
                            send(audioMessage.toString());
                            send(bytes);
                        }

                        // Step 3: Notify server all data sent
                        JSONObject doneMessage = new JSONObject();
                        doneMessage.put("type", "done");
                        send(doneMessage.toString());

                    } catch (Exception e) {
                        Log.e("WebSocket", "Error while sending data: " + e.getMessage());
                        listener.onError(e.getMessage());
                    }
                }

                @Override
                public void onMessage(String message) {
                    Log.d("WebSocket", "Message received: " + message);
                    try {
                        JSONObject response = new JSONObject(message);
                        Log.d("WebSocket Response",response.getJSONArray("questions").toString());
                        if (response.has("questions")) {
                            this.close(1000, "Data received successfully");
                            JSONArray array = response.getJSONArray("questions");
                            List<String> receivedQuestions = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                receivedQuestions.add(array.getString(i));
                            }
                            SpeakingQuestion speakingQuestion = new SpeakingQuestion();
                            speakingQuestion.setQuestions(receivedQuestions);
                            speakingQuestion.setQuestionPart(1);
                            if (context instanceof Activity) {
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.onReceive(speakingQuestion);
                                    }
                                });
                            }
                            // listener.onReceive(speakingQuestion);
                        }
                    } catch (Exception e) {
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onError(e.getMessage());
                                }
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
                }
            };

            webSocketClient.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadCompleteMockViaWebSocket(Context context, Map<String, File> audioFiles, List<String> questions) {
        try {
            URI uri = new URI("ws://10.0.0.102:8000/ws/uploadComplete");

            WebSocketClient webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    try {
                        // Step 1: Send questions
                        JSONObject questionMessage = new JSONObject();
                        questionMessage.put("type", "question");
                        questionMessage.put("questions", new JSONArray(questions));
                        send(questionMessage.toString());

                        // Step 2: Send audio files one by one
                        for (Map.Entry<String, File> entry : audioFiles.entrySet()) {
                            byte[] bytes = fileToBytes(entry.getValue());
                            String encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);

                            JSONObject audioMessage = new JSONObject();
                            audioMessage.put("type", "audio");
                            audioMessage.put("filename", entry.getKey());
                            send(audioMessage.toString());
                            send(bytes);
                        }

                        // Step 3: Notify server all data sent
                        JSONObject doneMessage = new JSONObject();
                        doneMessage.put("type", "done");
                        send(doneMessage.toString());

                    } catch (Exception e) {
                        Log.e("WebSocket", "Error while sending data: " + e.getMessage());
                        listener.onError(e.getMessage());
                    }
                }

                @Override
                public void onMessage(String message) {
                    Log.d("WebSocket", "Message received: " + message);
                    try {
                        JSONObject response = new JSONObject(message);
                        String overall = response.getString("overall_band");
                        String feedback = response.getString("feedback");
                        Log.d("Overall "+ overall,feedback);
                        this.close(1000, "Data received successfully");
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    completeListener.onTestResult(overall, feedback);
                                }
                            });
                        }
                    } catch (Exception e) {
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    completeListener.onError(e.getMessage());
                                }
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
                }
            };

            webSocketClient.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void requestTestUpload(Context context,Map<String,File> audioFiles, List<String> questions){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        VolleyMultipartRequest request = new VolleyMultipartRequest(
                Request.Method.POST,
                "http://10.0.0.102:8000/uploadIntroCompleted",
                audioFiles,
                null,
                questions,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d("Upload", "Success: " + new String(response.data));

                        String jsonString = new String(response.data);
                        JSONObject responseJson;
                        try {
                            responseJson = new JSONObject(jsonString);
                            completeListener.onTestResult(responseJson.getString("overall_band"),responseJson.getString("feedback"));
                        } catch (JSONException e) {
                            completeListener.onError(e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Upload", "Error: " + error.getMessage());
                        completeListener.onError("Error: " + error.getMessage());
                    }
                }
        );
        requestQueue.add(request);
    }


    public List<String> parseStringListFromNetworkResponse(NetworkResponse response) {
        List<String> result = new ArrayList<>();
        try {
            String jsonString = new String(response.data);
            JSONObject responseJson = new JSONObject(jsonString);
            JSONArray questions = responseJson.getJSONArray("questions");
            for(int i = 0; i < questions.length(); i++){
                result.add(questions.getString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private byte[] fileToBytes(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        fis.close();
        return bos.toByteArray();
    }

}



