package com.group7.mockexpert.api_helpers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TaskOneResultApiService {

    public interface ResultCallback {
        void onSuccess(int band, String feedback);
        void onError(String message);
    }

    public static void submitTaskOne(Context context, String question, String answer, String imageUrl, ResultCallback callback) {
        String url = "https://mock-expert-api.vercel.app/writing/resultTaskOne/";
        answer = answer.trim();
        try {
            JSONObject requestData = new JSONObject();
            requestData.put("question", question);
            requestData.put("answer", answer);
            requestData.put("imageUrl", imageUrl);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestData,
                    response -> {
                        try {
                            int band = response.getInt("overall_band");
                            String feedback = response.getString("feedback");
                            callback.onSuccess(band, feedback);
                        } catch (JSONException e) {
                            callback.onError("Error parsing result.");
                        }
                    },
                    error -> callback.onError("API error: " + error.toString())
            );

            RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
            queue.add(request);

        } catch (JSONException e) {
            callback.onError("Error creating request.");
        }
    }
}
