package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextTextPersonName;
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;
    Button button2;
    boolean isListening = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);
        editTextTextPersonName=findViewById(R.id.editTextTextPersonName);
        mSpeechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {

                String sentence="";
                ArrayList<String> matches=bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(matches!=null)
                {
                    Toast.makeText(MainActivity.this,"test",Toast.LENGTH_LONG).show();
                    sentence=matches.get(0);
                    //editTextTextPersonName.setText(sentence);
                    String [] words = sentence. split(" ");
                    for (int i=0;i<words.length;i++)
                    {
                        if(words[i].toLowerCase().equals("apple"))
                        {
                            editTextTextPersonName.setText("alarm");
                            break;
                        }
                    }
                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {
                String sentence="";
                ArrayList<String> matches=bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(matches!=null)
                {
                    Toast.makeText(MainActivity.this,"test",Toast.LENGTH_LONG).show();
                    sentence=matches.get(0);
                    //editTextTextPersonName.setText(sentence);
                    String [] words = sentence. split(" ");
                    for (int i=0;i<words.length;i++)
                    {
                        if(words[i].toLowerCase().equals("apple"))
                        {
                            editTextTextPersonName.setText("alarm");
                            break;
                        }
                    }
                }
            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

            }
    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED)){
                Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                finish();
                startActivity(intent);
                return;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view==button2){
            if(isListening==true){
                mSpeechRecognizer.stopListening();
                isListening=false;
                editTextTextPersonName.setHint("see input here");
                button2.setText("click to activate");

            }
            else{
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                isListening=true;
                editTextTextPersonName.setText("");
                editTextTextPersonName.setHint("listening");
                button2.setText("shutdown");
            }
        }
    }
}