package com.example.farzicoder.simplespeechtotext;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final int CHECK_CODE = 0x1;
    private Speaker speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkTTS();
        ((Button)findViewById(R.id.speak)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speaker.speak(((EditText)findViewById(R.id.input)).getText().toString());
            }
        });
    }

    private void checkTTS(){
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHECK_CODE){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                speaker = new Speaker(this);
                speaker.allow(true);
                speaker.speak(getString(R.string.start_speaking));
                Toast.makeText(MainActivity.this, "TTS Ready", Toast.LENGTH_LONG).show();
            }else {
                Intent install = new Intent();
                install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(install);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speaker.destroy();
    }
}
