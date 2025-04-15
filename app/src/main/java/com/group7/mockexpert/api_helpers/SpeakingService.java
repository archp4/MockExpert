package com.group7.mockexpert.api_helpers;


import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.group7.mockexpert.models.SpeakingQuestion;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeakingService {
    SpeakingAPIListener listener;
    String mainURL = "https://mock-expert-api.vercel.app/speaking/";

    public SpeakingService(SpeakingAPIListener listener){
        this.listener = listener;
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

    public void requestUpload(Context context,Map<String,File> audioFiles){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        VolleyMultipartRequest request = new VolleyMultipartRequest(
                Request.Method.POST,
                "http://10.0.0.102:8000/uploads",
                audioFiles,  // Send files with "files" field
                new HashMap<>(), // Headers (if needed)
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d("Upload", "Success: " + new String(response.data));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Upload", "Error: " + error.getMessage());
                    }
                }
        );

        requestQueue.add(request);
    }

    public void uploadSpeaking(Context context, Map<String, File> audioFiles) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        VolleyMultipartRequest request = new VolleyMultipartRequest(
                com.android.volley.Request.Method.POST,
                "http://10.0.0.102:8000/uploads",
                audioFiles,
                null, // Headers if needed
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d("Upload", "Success: " + new String(response.data));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Upload", "Error: " + error.toString());
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(3000000, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}

