package com.group7.mockexpert.api_helpers;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class QuestionTaskTwoService {

    public interface QuestionCallback {
        void onSuccess(String question);
        void onError(String message);
    }

    public static void fetchQuestion(Context context, int questionPart, QuestionCallback callback) {
        int difficultyLevel = 1;
        String url = "https://mock-expert-api.vercel.app/writing/questionTaskTwo?questionPart=" + questionPart + "&difficultLevel=" + difficultyLevel;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String question = response.getString("question");
                        callback.onSuccess(question);
                    } catch (JSONException e) {
                        callback.onError("JSON parsing error");
                    }
                },
                error -> {
                    String msg = "API error: " + error.toString();
                    Log.d("Task2Error", msg);
                    callback.onError(msg);
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        queue.add(request);
    }
}
