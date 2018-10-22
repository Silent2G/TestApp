package com.yleaf.stas.testapplication.models.items_response;

import com.google.gson.annotations.SerializedName;
import com.yleaf.stas.testapplication.models.items.PodcastItem;

import java.util.ArrayList;

public class JSONResponsePodcast {

    @SerializedName("results")
    private ArrayList<PodcastItem> results;

    public JSONResponsePodcast(ArrayList<PodcastItem> results) {
        this.results = results;
    }

    public ArrayList<PodcastItem> getResults() {
        return results;
    }

    public void setResults(ArrayList<PodcastItem> results) {
        this.results = results;
    }
}
