package edu.msrit.csrlearn;

import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import static android.view.KeyEvent.KEYCODE_NUMPAD_ENTER;

public class MainActivity extends AppCompatActivity {
    private String toSpeak = "";
    private TextToSpeech tts;
    private StateMaker SM;
    private State mState;
    private String input = "";
    private FileInputStream fileInputStream;
    private TextView questionText, inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionText = findViewById(R.id.question);
        inputText = findViewById(R.id.input);

        SM = new StateMaker(getApplicationContext());

        File mediaStorage = new File(Environment.getExternalStorageDirectory(), "assets");
        if (!mediaStorage.exists()) {
            if (!mediaStorage.mkdirs()) {
                Log.d("MainActivity", "Failed to create directory");
                Toast.makeText(this, "Failed to create directory", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
        }
        if (mediaStorage.exists()) {
            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        int result = tts.setLanguage(Locale.US);
                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("error", "This Language is not supported");
                        } else {
                            mState = getFileState("state1.json");
                            assert mState != null;
                            convertTextToSpeech(mState.speakOnStart);
                        }
                    } else
                        Log.e("error", "Initilization Failed!");
                }
            });
        }
    }

    private State getFileState(String nextPath) {
        StringBuilder path = new StringBuilder(Environment.getExternalStorageDirectory() + "/assets");
        String[] fileDetails = null;
        if (nextPath.contains("/")) {
            path.append("/");
            fileDetails = nextPath.split("/");
            for (String path1 : fileDetails) {
                if (!fileDetails[fileDetails.length - 1].equals(path1))
                    path.append(path1).append("/");
            }
            Log.d("PATH", String.valueOf(path));
        }
        File directory = new File(path.toString());

        // Reading files inside root directory
        File[] files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile();
            }
        });

        for (File f : files) {
            if (nextPath.contains("/")) {
                if (f.getName().equals(fileDetails[fileDetails.length - 1])) {
                    State helloState = SM.getState(f.getPath());
                    Log.d("MainAcContent", helloState.speakOnStart);
                    return helloState;
                }
            } else {
                if (f.getName().equals(nextPath)) {
                    State helloState = SM.getState(f.getPath());
                    Log.d("MainAcContent", helloState.speakOnStart);
                    return helloState;
                }
            }
        }
        return null;
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
                    mState = getFileState(object.getValue());
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
                        mState = getFileState(object.getValue());
                    }
                }
            }
            input = "";
            convertTextToSpeech(mState.speakOnStart);
        } else {
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
