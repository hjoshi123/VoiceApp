package edu.msrit.csrlearn;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;



import java.util.Locale;

import static android.view.KeyEvent.KEYCODE_NUMPAD_ENTER;

public class MainActivity extends AppCompatActivity {


    private LinearLayout linearLayout;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private String toSpeak = "";
    private TextToSpeech tts;

    private AudioManager.OnAudioFocusChangeListener mAudioChangeListner =
            new AudioManager.OnAudioFocusChangeListener(){
                @Override
                public void onAudioFocusChange(int focusChange){
                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_GAIN)
                        //we gained audiofocus so we have to start playing the song
                        mMediaPlayer.start();
                    else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        //we lost the audiofocus so release the mediaplayer resources
                        releaseMediaPlayer();
                    }
                }
            };

    //releasing media resources
    private MediaPlayer.OnCompletionListener mMediaOnCompletionListner = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.linear_layout);
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
                        ConvertTextToSpeech(toSpeak);
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
        tts.setLanguage(Locale.US);
        tts.speak("Text to say aloud", TextToSpeech.QUEUE_ADD, null);


        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    private void ConvertTextToSpeech(String text) {
        // TODO Auto-generated method stub
        if(text==null||"".equals(text))
        {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }else
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("Key", event.toString());
        switch (keyCode){
            case KeyEvent.KEYCODE_ENTER:
                Log.d("MainActivity","Main Enter was pressed");
                //playAudio();
                ConvertTextToSpeech(toSpeak);
                toSpeak = "";
                return true;

            case KEYCODE_NUMPAD_ENTER:
                Log.d("MainActivity","Numpad Enter was pressed");
                //playAudio();
                ConvertTextToSpeech(toSpeak);
                toSpeak = "";
                return true;

            default:
                char unicodeChar = (char)event.getUnicodeChar();
                toSpeak = toSpeak + unicodeChar + "";
                Log.d("Speak", toSpeak);
        }
        return false;
    }

    private void playAudio() {
        releaseMediaPlayer();
        int result = mAudioManager.requestAudioFocus(mAudioChangeListner,
                AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
            mMediaPlayer = MediaPlayer.create(this,R.raw.game_of_thrones);
            mMediaPlayer.start();

            mMediaPlayer.setOnCompletionListener(mMediaOnCompletionListner);

        }
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mAudioChangeListner);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
