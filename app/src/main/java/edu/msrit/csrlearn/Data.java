package edu.msrit.csrlearn;

/**
 * Created by HemantJ on 15/04/18.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("string_to_speak")
    @Expose
    private String stringToSpeak;
    @SerializedName("hashmap")
    @Expose
    private List<Hashmap> hashmap = null;

    public String getStringToSpeak() {
        return stringToSpeak;
    }

    public void setStringToSpeak(String stringToSpeak) {
        this.stringToSpeak = stringToSpeak;
    }

    public List<Hashmap> getHashmap() {
        return hashmap;
    }

    public void setHashmap(List<Hashmap> hashmap) {
        this.hashmap = hashmap;
    }

}