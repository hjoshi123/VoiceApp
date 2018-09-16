package edu.msrit.csrlearn;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;


import java.util.Locale;

import static android.view.KeyEvent.KEYCODE_NUMPAD_ENTER;

public class MainActivity extends AppCompatActivity {
    private String toSpeak = "";
    private TextToSpeech tts;
    private StateMaker SM;
    private State mState;
    private String input = "";
    private TextView questionText, inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionText = findViewById(R.id.question);
        inputText = findViewById(R.id.input);
        SM = new StateMaker(getApplicationContext());
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                    else{
                        mState = SM.getState("state1.json");
                        convertTextToSpeech(mState.speakOnStart);
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
    }

    private void convertTextToSpeech(String text) {
        if(text==null||"".equals(text)) {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_ADD, null, null);
        } else
            tts.speak(text, TextToSpeech.QUEUE_ADD, null,null);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        Log.d("Key", event.toString() + "\n" + keyCode);
        String key = KeyFinder.getKeyPressed(keyCode);
        boolean flag = false;

        if (key.equals("enter") && !input.equals("")) {
            for (Hashmap object : mState.keys) {
                if (object.getKey().equals(input)) {
                    Log.i("MainActivity", object.getString());
                    toSpeak = object.getString();
                    tts.speak(toSpeak, TextToSpeech.QUEUE_ADD, null, null);
                    mState = SM.getState(object.getValue());
                    flag = true;
                    break;
                }

            }



            if (!flag) {
                Log.i("to_speak", "* case activated");
                for (Hashmap object : mState.keys) {
                    if (object.getKey().equals("*")) {
                        toSpeak = object.getString();
                        convertTextToSpeech(toSpeak);
                        mState = SM.getState(object.getValue());
                    }
                }
            }

            input = "";
            convertTextToSpeech(mState.speakOnStart);

        }

        else {
            input = input + key;
        }


        updateScreen();

        return false;
    }

    void updateScreen() {
        questionText.setText(mState.speakOnStart);
        inputText.setText(input);
    }


}
