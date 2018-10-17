package com.yleaf.stas.testapplication.models;

import com.google.gson.annotations.SerializedName;

public class JSONResponse {

    @SerializedName("feed")
    Feed feed;

    public JSONResponse(Feed feed) {
        this.feed = feed;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}
