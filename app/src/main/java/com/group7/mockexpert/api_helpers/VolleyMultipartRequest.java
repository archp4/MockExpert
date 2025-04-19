package com.group7.mockexpert.api_helpers;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class VolleyMultipartRequest extends Request<NetworkResponse> {
    private final Map<String, String> headers;
    private final Map<String, File> files;
    private final List<String> questions;


    private final Response.Listener<NetworkResponse> listener;
    private final String boundary = "apiclient-" + System.currentTimeMillis();

    public VolleyMultipartRequest(int method, String url, Map<String, File> files,
                                  Map<String, String> headers,
                                  List<String> questions,
                                  Response.Listener<NetworkResponse> listener,
                                  Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.files = files;
        this.headers = headers;
        this.listener = listener;
        this.questions = questions;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data; boundary=" + boundary;
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try {
            if (files != null) {
                for (Map.Entry<String, File> entry : files.entrySet()) {
                    buildFilePart(dos, entry.getValue());
                }
            }
            if (questions != null) {
                for (String value : questions) {
                    buildStringPart(dos, "questions", value);
                }
            }
            dos.writeBytes("--" + boundary + "--\r\n");
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    private void buildFilePart(DataOutputStream dos, File file) throws IOException {
        String fileName = file.getName();
        dos.writeBytes("--" + boundary + "\r\n");
        dos.writeBytes("Content-Disposition: form-data; name=\"files\"; filename=\"" + fileName + "\"\r\n");
        dos.writeBytes("Content-Type: audio/mpeg\r\n\r\n");

        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            dos.write(buffer, 0, bytesRead);
        }
        fis.close();
        dos.writeBytes("\r\n");
    }

    private void buildStringPart(DataOutputStream dos, String paramName, String value) throws IOException {
        dos.writeBytes("--" + boundary + "\r\n");
        dos.writeBytes("Content-Disposition: form-data; name=\"" + paramName + "\"\r\n\r\n");
        dos.writeBytes(value + "\r\n");
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        listener.onResponse(response);
    }
}
