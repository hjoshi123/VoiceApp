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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                        toSpeak = "Enter the result of 7 + 2";
                        convertTextToSpeech(toSpeak);
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
//        tts.setLanguage(Locale.US);
//        tts.speak("Text to say aloud", TextToSpeech.QUEUE_ADD, null,null);
    }

    private void convertTextToSpeech(String text) {
        if(text==null||"".equals(text)) {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,null);
        } else
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,null);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("Key", event.toString());
        switch (keyCode){
            case KeyEvent.KEYCODE_ENTER:
                Log.d("MainActivity","Main Enter was pressed");
                convertTextToSpeech(toSpeak);
                toSpeak = "";
                return true;
            case KEYCODE_NUMPAD_ENTER:
                Log.d("MainActivity","Numpad Enter was pressed");
                convertTextToSpeech(toSpeak);
                toSpeak = "";
                return true;
            case KeyEvent.KEYCODE_NUMPAD_9:
                toSpeak = "Congrats!! You Entered the correct answer";
                convertTextToSpeech(toSpeak);
                return true;

            default:
                toSpeak = "Sorry wrong answer";
                convertTextToSpeech(toSpeak);
        }
        return false;
    }
}
