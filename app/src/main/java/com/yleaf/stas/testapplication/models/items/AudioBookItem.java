package com.yleaf.stas.testapplication.models.items;

import com.google.gson.annotations.SerializedName;

public class AudioBookItem {

    @SerializedName("artistName")
    String artistName;

    @SerializedName("collectionName")
    String collectionName;

    @SerializedName("artworkUrl100")
    String artworkUrl100;

    @SerializedName("releaseDate")
    String releaseDate;

    @SerializedName("previewUrl")
    String previewUrl;

    @SerializedName("description")
    String description;

    public AudioBookItem(String artistName, String collectionName, String artworkUrl100, String releaseDate, String previewUrl, String description) {
        this.artistName = artistName;
        this.collectionName = collectionName;
        this.artworkUrl100 = artworkUrl100;
        this.releaseDate = releaseDate;
        this.previewUrl = previewUrl;
        this.description = description;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
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

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
