package com.group7.mockexpert;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaScannerConnection;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
            // Refresh Media Storage to show file
            MediaScannerConnection.scanFile(this,
                    new String[]{audioFile.getAbsolutePath()},
                    null,
                    (path, uri) -> {});

            Toast.makeText(this, "Recording saved: " + audioFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
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
