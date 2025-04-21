package com.group7.mockexpert.api_helpers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TaskTwoResultApiService {

    public interface ResultCallback {
        void onSuccess(int band, String feedback);
        void onError(String message);
    }

    public static void submitTaskTwo(Context context, String question, String answer, ResultCallback callback) {
        String url = "https://mock-expert-api.vercel.app/writing/resultTaskTwo/";
        answer = answer.trim();
        try {
            JSONObject json = new JSONObject();
            json.put("question", question);
            json.put("answer", answer);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json,
                    response -> {
                        try {
                            int band = response.getInt("overall_band");
                            String feedback = response.getString("feedback");
                            callback.onSuccess(band, feedback);
                        } catch (JSONException e) {
                            callback.onError("Error parsing response.");
                        }
                    },
                    error -> callback.onError("Submission failed.")
            );

            RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
            queue.add(request);
        } catch (JSONException e) {
            callback.onError("Error creating request.");
        }
    }
}
