package com.yleaf.stas.testapplication.models.items_response;

import com.google.gson.annotations.SerializedName;
import com.yleaf.stas.testapplication.models.items.MovieItem;

import java.util.ArrayList;

public class JSONResponseMovie {

    @SerializedName("results")
    private ArrayList<MovieItem> results;

    public JSONResponseMovie(ArrayList<MovieItem> results) {
        this.results = results;
    }

    public ArrayList<MovieItem> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieItem> results) {
        this.results = results;
    }
}
