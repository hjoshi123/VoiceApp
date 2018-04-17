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
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,null);
        } else
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,null);
    }

    /**
     *
     * @param keycode keycode from the onKeyUp method
     * @return String the keys of the json files
     */
    //TODO think of optimizing this -- How will you optimize this?
    public String getKeyPressed(int keycode) {
        switch (keycode) {
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_NUMPAD_ENTER:
                return "enter";
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_NUMPAD_0:
                return "0";
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_NUMPAD_1:
                return "1";
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_NUMPAD_2:
                return "2";
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_NUMPAD_3:
                return "3";
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_NUMPAD_4:
                return "4";
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_NUMPAD_5:
                return "5";
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_NUMPAD_6:
                return "6";
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_NUMPAD_7:
                return "7";
            case KeyEvent.KEYCODE_8:
            case KeyEvent.KEYCODE_NUMPAD_8:
                return "8";
            case KeyEvent.KEYCODE_9:
            case KeyEvent.KEYCODE_NUMPAD_9:
                return "9";
            default:
                return "*";
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("Key", event.toString());
        String key = getKeyPressed(keyCode);
        boolean flag = false;
        for (Hashmap object: mState.keys) {
            if (object.getKey().equals(key)) {
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
            for (Hashmap object:mState.keys) {
                if (object.getKey().equals("*")) {
                    toSpeak = object.getString();
                    convertTextToSpeech(toSpeak);
                    mState = SM.getState(object.getValue());
                }
            }
        }
        convertTextToSpeech(mState.speakOnStart);
        return false;
    }
}
