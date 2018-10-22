package com.yleaf.stas.testapplication.models.items;

import com.google.gson.annotations.SerializedName;

public class MovieItem {

    @SerializedName("artistName")
    String artistName;

    @SerializedName("trackName")
    String trackName;

    @SerializedName("previewUrl")
    String previewUrl;

    @SerializedName("artworkUrl100")
    String artworkUrl100;

    @SerializedName("releaseDate")
    String releaseDate;

    @SerializedName("longDescription")
    String longDescription;

    public MovieItem(String artistName, String trackName, String previewUrl, String artworkUrl100, String releaseDate, String longDescription) {
        this.artistName = artistName;
        this.trackName = trackName;
        this.previewUrl = previewUrl;
        this.artworkUrl100 = artworkUrl100;
        this.releaseDate = releaseDate;
        this.longDescription = longDescription;
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

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
