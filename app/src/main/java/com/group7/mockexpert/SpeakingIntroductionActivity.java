package com.group7.mockexpert;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaScannerConnection;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.group7.mockexpert.api_helpers.SpeakingAPIListener;
import com.group7.mockexpert.api_helpers.SpeakingCompleteListener;
import com.group7.mockexpert.api_helpers.SpeakingService;
import com.group7.mockexpert.models.SpeakingQuestion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeakingIntroductionActivity extends AppCompatActivity implements SpeakingAPIListener, SpeakingCompleteListener {

    private MediaRecorder mediaRecorder;
    private Button startButton;
    private boolean isRecording = false;
    private File audioFile;
    private TextView textView;
    private TextView indexView;
    private List<String> questions;
    private int index = 0;
    private static final int REQUEST_PERMISSIONS = 200;
    private static final int SPEECH_REQUEST_CODE = 102;

    private final String [] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Map<String,File> audioFiles = new HashMap<String,File>();
    SpeakingService service;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_speaking_introduction);
        startButton = findViewById(R.id.btn_start);
        textView = findViewById(R.id.tv_introduction);
        indexView = findViewById(R.id.tv_question);
        if (!checkPermissions()) {
            requestPermissions();
        }
        startButton.setOnClickListener(v -> toggleRecording());
        service = new SpeakingService(this,this);
        questions = service.requestSpeakingIntro().getQuestions();
        updatedQuestion();
    }

    private void requestAPI(){
        Log.e("WebSocket","API Requested");
        service.uploadViaWebSocket(this, audioFiles, questions);
    }

    private void completedAPI(){
        Log.d("Test Completed","Total Question Answer:" + (index+1));
        service.uploadCompleteMockViaWebSocket(this, audioFiles, questions);
    }
    private void checkPermissions1() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSIONS);
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    private boolean checkPermissions(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }


    private void toggleRecording() {
        if (isRecording) {
            stopRecording();
        } else {
            startRecording();
        }
    }

    private void startRecording() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            return;
        }

        mediaRecorder = new MediaRecorder(this);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        if (path != null) {
            String fileName= questions.get(index-1).replace(" ","_").replace(".","_").replace("?","");
            audioFile = new File(path, fileName +".mp3");
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
        } else {
            Toast.makeText(this, "Storage error", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            startButton.setText("Stop Recording");
            Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to start recording", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (mediaRecorder == null) {
            Toast.makeText(this, "Recorder not initialized", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            startButton.setText(R.string.start_recording);
            MediaScannerConnection.scanFile(this,
                    new String[]{audioFile.getAbsolutePath()},
                    null,
                    (path, uri) -> {});
            audioFiles.put(audioFile.getName(),new File(audioFile.getAbsolutePath()));

            if (questions.toArray().length > index)
            {
                updatedQuestion();
            } else if (index == 3) {
                requestAPI();
                textView.setText("Please Wait\nwe are processing you response.");
            } else
            {
                textView.setText("End of Session\nPlease Wait\nwe are processing you response.");
//                startButton.setEnabled(false);
                completedAPI();
            }
        } catch (Exception e) {
            e.getMessage();
            Toast.makeText(this, "Failed to stop recording", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = (results != null && !results.isEmpty()) ? results.get(0) : "No speech detected";
            Toast.makeText(this,spokenText,Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onReceive(SpeakingQuestion question) {
        Log.d("onReceive",question.toString());
        questions.addAll(question.getQuestions());
        updatedQuestion();
    }

    private void updatedQuestion(){
        textView.setText(questions.get(index));
        index++;
        indexView.setText(String.format("Question %d", index));
    }

    @Override
    public void onTestResult(String overall, String feedback) {
        indexView.setText("Band : " + overall);
        textView.setText("Feedback :\n"+ feedback);
        textView.setTextSize(14);
        startButton.setEnabled(false);
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this,"errorMessage",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
