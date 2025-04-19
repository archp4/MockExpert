package com.group7.mockexpert;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
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

public class CueCard extends AppCompatActivity implements SpeakingCompleteListener, SpeakingAPIListener {
    private MediaRecorder mediaRecorder;
    private Button startButton;
    private boolean isRecording = false;
    private File audioFile;
    private TextView textView;
    private  TextView indexView;
    private SpeakingService speakingService;
    private static final int REQUEST_PERMISSIONS = 200;
    private final String [] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final int SPEECH_REQUEST_CODE = 102;
    private Map<String,File> audioFiles = new HashMap<String,File>();
    private List<String> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cue_card_detail);
        textView = findViewById(R.id.tv_question_cc);
        startButton = findViewById(R.id.btn_start);
        textView.setText(R.string.loading_question);
        indexView=findViewById(R.id.tv_speakingPractice_cc);
        if (!checkPermissions()) {
            requestPermissions();
        }
        startButton.setEnabled(false);
        speakingService = new SpeakingService(this, this);
        startButton.setOnClickListener(v -> toggleRecording());
        speakingService.requestSpeakingPartTwo(this);
    }

    private boolean checkPermissions(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
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
            audioFile = new File(path, "CueCard.mp3");
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
        } else {
            Toast.makeText(this, "Storage error", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            startButton.setText(R.string.stop_recording);
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
            completedAPI();
            // Toast.makeText(this, "Recording saved: " + audioFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to stop recording", Toast.LENGTH_SHORT).show();
        }
    }

    private void completedAPI(){
        speakingService.uploadCompleteMockViaWebSocket(this, audioFiles, questionsList);
        textView.setText("Processing Your Test\n Please Wait");
        startButton.setEnabled(false);
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
        StringBuilder result = new StringBuilder();
        for (String q: question.getQuestions()) {
            result.append(q).append("\n");
        }
        textView.setText(result);
        startButton.setEnabled(true);
        questionsList = new ArrayList<String>();
        questionsList.add(result.toString());
    }

    @Override
    public void onTestResult(String overall, String feedback) {
        indexView.setText("Band : " + overall);
        textView.setText("Feedback :\n"+ feedback);
        textView.setTextSize(14);
        startButton.setEnabled(false);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
