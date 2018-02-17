package edu.msrit.csrlearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.linear_layout);


        linearLayout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                makeToast(keyEvent);
                Log.d("Key", i + " " + keyEvent.toString() );
                return false;
            }


        });


    }

    public void makeToast(KeyEvent keyEvent) {
        Toast.makeText(this, keyEvent.toString(), Toast.LENGTH_SHORT).show();
    }


}
