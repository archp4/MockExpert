package com.group7.mockexpert;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

public class CueCard extends AppCompatActivity {
    private MediaRecorder mediaRecorder;
    private Button startButton;
    private boolean isRecording = false;
    private File audioFile;

    private static final int REQUEST_PERMISSIONS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cue_card_detail);

        startButton = findViewById(R.id.btn_start);

        checkPermissions();

        startButton.setOnClickListener(v -> toggleRecording());
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

            // Refresh Media Storage to show file
            MediaScannerConnection.scanFile(this,
                    new String[]{audioFile.getAbsolutePath()},
                    null,
                    (path, uri) -> {});

            Toast.makeText(this, "Recording saved: " + audioFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
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
}
