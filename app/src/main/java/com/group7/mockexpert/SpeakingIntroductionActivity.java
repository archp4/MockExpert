package com.group7.mockexpert;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaScannerConnection;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.group7.mockexpert.api_helpers.SpeakingAPIListener;
import com.group7.mockexpert.api_helpers.SpeakingService;
import com.group7.mockexpert.models.SpeakingQuestion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpeakingIntroductionActivity extends AppCompatActivity implements SpeakingAPIListener {

    private MediaRecorder mediaRecorder;
    private Button startButton;
    private boolean isRecording = false;
    private File audioFile;
    private TextView textView;
    private List<String> questions;
    private int index = 0;
    private static final int REQUEST_PERMISSIONS = 200;
    private static final int PICK_AUDIO_REQUEST_CODE = 101;
    private static final int SPEECH_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_speaking_introduction);
        requestAPI();
        startButton = findViewById(R.id.btn_start);
        textView = findViewById(R.id.tv_introduction);
        checkPermissions();
        startButton.setOnClickListener(v -> toggleRecording());
    }

    private void requestAPI(){
        Log.e("Log","API Requested");
        SpeakingService service = new SpeakingService(this);
        service.requestSpeakingPartOne(this);
    }
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSIONS);
        }
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

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (path != null) {
            audioFile = new File(path, "introAudio.3gp");
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
            startButton.setText("Start Recording");

            if (questions.toArray().length > index)
            {
                updatedQuestion();
            }
            else
            {
                Toast.makeText(this,"End of Session",Toast.LENGTH_SHORT).show();
                startButton.setEnabled(false);
            }

            MediaScannerConnection.scanFile(this,
                    new String[]{audioFile.getAbsolutePath()},
                    null,
                    (path, uri) -> {});
            convertSpeechToText(audioFile.getAbsolutePath());
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

    private void convertSpeechToText(String audioURL) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_AUDIO_SOURCE, audioURL);
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(SpeakingIntroductionActivity.this,"Speech recognition not supported on this device",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onReceive(SpeakingQuestion question) {
        questions = question.getQuestions();
        updatedQuestion();
    }

    private void updatedQuestion(){
        textView.setText(questions.get(index));
        index++;
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
