package com.group7.mockexpert.api_helpers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class QuestionApiService {

    public interface QuestionCallback {
        void onSuccess(String question, String imageUrl);
        void onError(String message);
    }

    public static void fetchQuestion(Context context, int questionType, QuestionCallback callback) {
        String url = "https://mock-expert-api.vercel.app/writing/questionTaskOne?questionType=" + questionType;

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String question = response.getString("question");
                        String imageUrl = response.getString("imageUrl");
                        callback.onSuccess(question, imageUrl);
                    } catch (JSONException e) {
                        callback.onError("JSON Parsing Error");
                    }
                },
                error -> callback.onError("API Error: " + error.getMessage())
        );

        queue.add(request);
    }
}
