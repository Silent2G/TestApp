package com.yleaf.stas.testapplication.models.items_response;

import com.google.gson.annotations.SerializedName;
import com.yleaf.stas.testapplication.models.items.AudioBookItem;

import java.util.ArrayList;

public class JSONResponseAudioBook {

    @SerializedName("results")
    private ArrayList<AudioBookItem> results;

    public JSONResponseAudioBook(ArrayList<AudioBookItem> results) {
        this.results = results;
    }

    public ArrayList<AudioBookItem> getResults() {
        return results;
    }

    public void setResults(ArrayList<AudioBookItem> results) {
        this.results = results;
    }
}
