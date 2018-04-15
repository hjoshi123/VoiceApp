package edu.msrit.csrlearn;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;


import java.util.Locale;

import static android.view.KeyEvent.KEYCODE_NUMPAD_ENTER;

public class MainActivity extends AppCompatActivity {
    private String toSpeak = "";
    private TextToSpeech tts;
    private StateMaker SM;
    private State mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                        toSpeak = "Hello World";
                        convertTextToSpeech(toSpeak);
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
//        tts.setLanguage(Locale.US);
//        tts.speak("Text to say aloud", TextToSpeech.QUEUE_ADD, null,null);

        mState = SM.getState("state1.json");
    }

    private void convertTextToSpeech(String text) {
        if(text==null||"".equals(text)) {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,null);
        } else
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,null);
    }

    public String getKeyPressed(int keycode) {
        switch (keycode) {
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_NUMPAD_ENTER:
                return "enter";
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_NUMPAD_1:
                return "1";
            default:
                return "*";
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("Key", event.toString());
        String key = getKeyPressed(keyCode);
        //convertTextToSpeech(mState.speakOnStart);
        boolean flag = false;
        for (Hashmap object: mState.keys) {
            if (object.getKey().equals(key)) {
                convertTextToSpeech(object.getString());
                mState = SM.getState(object.getValue());
                flag = true;
                break;
            }

        }
        if (flag == false) {
            for (Hashmap object:mState.keys) {
                if (object.getKey().equals("*")) {
                    convertTextToSpeech(object.getString());
                    mState = SM.getState(object.getValue());
                }
            }
        }
        convertTextToSpeech(mState.speakOnStart);
        return false;
    }
}
