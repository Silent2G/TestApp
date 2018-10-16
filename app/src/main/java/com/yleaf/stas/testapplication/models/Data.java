package com.yleaf.stas.testapplication.models;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("id")
    String id;

    @SerializedName("artistName")
    String artistName;

    @SerializedName("name")
    String name;

    @SerializedName("artworkUrl100")
    String artworkUrl100;

    public Data(String id, String artistName, String name, String artworkUrl100) {
        this.id = id;
        this.artistName = artistName;
        this.name = name;
        this.artworkUrl100 = artworkUrl100;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }
}
