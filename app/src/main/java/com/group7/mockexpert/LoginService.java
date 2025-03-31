package com.group7.mockexpert;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;

interface LoginListener{
    void onLoginSuccessful();
    void onLoginError();
}

public class LoginService {

    LoginListener loginListener;

    void onLogin(String username, String password, Context context){

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://mock-expert-api.vercel.app/auth/login/";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show();
                        loginListener.onLoginSuccessful();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleVolleyError(error, context);
                        loginListener.onLoginError();
                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                JSONObject params = new JSONObject();
                try {
                    params.put("username", username);
                    params.put("password", password);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                return params.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        postRequest.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(10000, com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES, com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(postRequest);


    }

    private static void handleVolleyError(VolleyError error, Context context) {
        String message = "";
        if (error.networkResponse != null) {
            int statusCode = error.networkResponse.statusCode;
            String responseData = new String(error.networkResponse.data, StandardCharsets.UTF_8);
             message =  "Login failed" + ". Status Code: " + statusCode + ". Response: " + responseData;
        } else {
             message = "Login failed" + ". Network error. See Logcat.";
        }
        VolleyLog.e(message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
