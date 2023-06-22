package com.example.recordcallapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText editText;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private final String directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private final String audioFilePath = directoryPath + "/audioRecording.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting activity_main");

        if (!checkPermissions()) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS,
                    Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },1);
        }

        EditText editText = findViewById(R.id.editTextText);
        Button callButton = findViewById(R.id.button);
        Button contactScreenButton = findViewById(R.id.contactScreenButton);
        Button startRecording = findViewById(R.id.startRecording);
        Button stopRecording = findViewById(R.id.stopRecording);
        Button startPlaying = findViewById(R.id.startPlaying);
        Button stopPlaying = findViewById(R.id.stopPlaying);

        String phoneNumber;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            phoneNumber = bundle.getString("phone_number");
            editText.setText(phoneNumber);
        }

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: call button");
                String number = editText.getText().toString();
                String normalizedNumber = PhoneNumberUtils.normalizeNumber(number);

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+normalizedNumber));

                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                startActivity(callIntent);
            }
        });

        contactScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: contactScreenButton");

                Intent intent = new Intent(MainActivity.this, ContactsProcessor.class);
                startActivity(intent);
            }
        });

        startRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: startRecording button");

                if (mediaRecorder != null) {
                    return;
                }

                if (checkPermissions()) {
                    try {
                        if (mediaRecorder != null) {
                            mediaRecorder.release();
                            mediaRecorder = null;
                        }

                        mediaRecorder = new MediaRecorder();
                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                        mediaRecorder.setOutputFile(audioFilePath);

                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        Toast.makeText(MainActivity.this, "Recording started",
                                Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },1);
                }
            }
        });

        stopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: stopRecording button");

                if (mediaRecorder != null) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    Toast.makeText(MainActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });

        startPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: startPlaying button");

                try {
                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }

                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(audioFilePath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this, "Start playing", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        stopPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: stopPlaying button");

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    Toast.makeText(MainActivity.this, "Stopped playing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean checkPermissions() {
        int first = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE);
        int second = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_CONTACTS);
        int third = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO);
        int fourth = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int fifth = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return first == PackageManager.PERMISSION_GRANTED &&
                second == PackageManager.PERMISSION_GRANTED &&
                third == PackageManager.PERMISSION_GRANTED &&
                fourth == PackageManager.PERMISSION_GRANTED &&
                fifth == PackageManager.PERMISSION_GRANTED;
    }
}