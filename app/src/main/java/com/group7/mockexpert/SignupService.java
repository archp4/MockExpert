package com.group7.mockexpert;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

interface SignUpListener {
    void onSignUpSuccess();
    void onSignUpError();
}

public class SignupService {

    private SignUpListener signUpListener;

    public SignupService(SignUpListener listener) {
        this.signUpListener = listener;
    }

    public void registerUser(String fullName, String email, String phone, String username, String password, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://mock-expert-api.vercel.app/auth/register/";

        JSONObject params = new JSONObject();
        try {
            params.put("full_name", fullName);
            params.put("email", email);
            params.put("phone_number", phone);
            params.put("username", username);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            signUpListener.onSignUpError();
            return;
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show();
                        signUpListener.onSignUpSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleVolleyError(error, context);
                        signUpListener.onSignUpError();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(postRequest);
    }

    private void handleVolleyError(VolleyError error, Context context) {
        String message;
        if (error.networkResponse != null) {
            int statusCode = error.networkResponse.statusCode;
            String responseData = new String(error.networkResponse.data, StandardCharsets.UTF_8);
            message = "Sign-up failed. Status Code: " + statusCode + ". Response: " + responseData;
        } else {
            message = "Sign-up failed. Network error. See logs.";
        }
        VolleyLog.e(message);
        Toast.makeText(context, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
    }
}
