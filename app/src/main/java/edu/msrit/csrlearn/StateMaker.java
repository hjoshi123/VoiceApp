package edu.msrit.csrlearn;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by HemantJ on 15/04/18.
 */

public class StateMaker {
    Context mContext;
    String json = null;

    StateMaker (Context context) {
        this.mContext = context;
    }

    public State getState(String fileName) {
        State newState = new State();
        try {

            InputStream is = mContext.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Data data = new Gson().fromJson(json, Data.class);
            Log.d("to_speak", data.getStringToSpeak());
            Log.d("to_speak", data.getHashmap().get(0).getKey());

            newState.speakOnStart = data.getStringToSpeak();
            newState.keys = data.getHashmap();
            return newState;
        } catch (Exception e){
            e.printStackTrace();
            newState.speakOnStart = "we are dead";
            newState.keys = null;
            return newState;
        }

    }
}
