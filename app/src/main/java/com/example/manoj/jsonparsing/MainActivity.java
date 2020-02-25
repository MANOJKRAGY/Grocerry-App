package com.example.manoj.jsonparsing;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
     Button click;
     public static TextView data;
     TextToSpeech textToSpeech;
     int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click = (Button) findViewById(R.id.button);
        data = (TextView) findViewById(R.id.fetcheddata);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData process = new FetchData();
                process.execute();
            }
        });
        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = textToSpeech.setLanguage(Locale.UK);
                } else {
                    Toast.makeText(getApplicationContext(), "Feather NOT Supported in your device", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
   public void doSomething(View v){
       switch (v.getId()){
           case R.id.bspeak:
               if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == textToSpeech.LANG_MISSING_DATA){
                   Toast.makeText(getApplicationContext(),"Feature not supported in your device",Toast.LENGTH_SHORT).show();
               }else{
                   String share = data.getText().toString();
                   textToSpeech.speak(share,TextToSpeech.QUEUE_FLUSH,null,null);
               }
               break;
           case R.id.bstopspeaking:
               if(textToSpeech != null){
                   textToSpeech.stop();
               }
               break;
       }
   }
    protected void OnDestroy(){
        super.onDestroy();
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }




}
