package com.yleaf.stas.testapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable {
    @SerializedName("id")
    int id;

    @SerializedName("kind")
    String kind;

    @SerializedName("artistName")
    String artistName;

    @SerializedName("name")
    String name;

    @SerializedName("artworkUrl100")
    String artworkUrl100;

    public Data(int id, String kind, String artistName, String name, String artworkUrl100) {
        this.id = id;
        this.kind = kind;
        this.artistName = artistName;
        this.name = name;
        this.artworkUrl100 = artworkUrl100;
    }

    public Data(Parcel parcel) {
        this.id = parcel.readInt();
        this.kind = parcel.readString();
        this.artistName = parcel.readString();
        this.name = parcel.readString();
        this.artworkUrl100 = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.kind);
        parcel.writeString(this.artistName);
        parcel.writeString(this.name);
        parcel.writeString(this.artworkUrl100);
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel parcel) {
            return new Data(parcel);
        }

        @Override
        public Data[] newArray(int i) {
            return new Data[i];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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
