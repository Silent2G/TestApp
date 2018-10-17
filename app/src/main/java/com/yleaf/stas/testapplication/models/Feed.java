package com.yleaf.stas.testapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Feed {

    @SerializedName("results")
    private ArrayList<Data> results;

    public Feed(ArrayList<Data> results) {
        this.results = results;
    }

    public ArrayList<Data> getResults() {
        return results;
    }

    public void setResults(ArrayList<Data> results) {
        this.results = results;
    }
}
