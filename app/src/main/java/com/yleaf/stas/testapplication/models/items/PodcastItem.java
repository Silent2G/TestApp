package com.yleaf.stas.testapplication.models.items;

import com.google.gson.annotations.SerializedName;

public class PodcastItem {

    @SerializedName("artistName")
    String artistName;

    @SerializedName("trackName")
    String trackName;

    @SerializedName("artworkUrl600")
    String artworkUrl600;

    @SerializedName("releaseDate")
    String releaseDate;

    public PodcastItem(String artistName, String trackName, String artworkUrl600, String releaseDate) {
        this.artistName = artistName;
        this.trackName = trackName;
        this.artworkUrl600 = artworkUrl600;
        this.releaseDate = releaseDate;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtworkUrl600() {
        return artworkUrl600;
    }

    public void setArtworkUrl600(String artworkUrl600) {
        this.artworkUrl600 = artworkUrl600;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
