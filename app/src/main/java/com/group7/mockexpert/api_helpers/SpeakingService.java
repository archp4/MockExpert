package com.group7.mockexpert.api_helpers;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.group7.mockexpert.models.SpeakingQuestion;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

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
}

