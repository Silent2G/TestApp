package com.yleaf.stas.testapplication.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Data implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("kind")
    @Expose
    private String kind;

    @SerializedName("artistName")
    @Expose
    private String artistName;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("artworkUrl100")
    @Expose
    private String artworkUrl100;

    private boolean favorite;

    public Data(int id, String kind, String artistName, String name, String artworkUrl100) {
        this.id = id;
        this.kind = kind;
        this.artistName = artistName;
        this.name = name;
        this.artworkUrl100 = artworkUrl100;
        this.favorite = false;
    }

    public Data(Parcel parcel) {
        this.id = parcel.readInt();
        this.kind = parcel.readString();
        this.artistName = parcel.readString();
        this.name = parcel.readString();
        this.artworkUrl100 = parcel.readString();
        this.favorite = parcel.readByte() != 0;
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
        parcel.writeByte((byte) (this.favorite ? 1 : 0));
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
