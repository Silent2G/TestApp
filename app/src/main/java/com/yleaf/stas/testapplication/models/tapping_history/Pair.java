package com.yleaf.stas.testapplication.models.tapping_history;

import android.os.Parcel;
import android.os.Parcelable;

public class Pair implements Parcelable {
    private String categoryName;
    private int firstVisiblePositionNumber;

    public Pair(String categoryName, int firstVisiblePositionNumber) {
        this.categoryName = categoryName;
        this.firstVisiblePositionNumber = firstVisiblePositionNumber;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getFirstVisiblePositionNumber() {
        return firstVisiblePositionNumber;
    }

    public void setFirstVisiblePositionNumber(int firstVisiblePositionNumber) {
        this.firstVisiblePositionNumber = firstVisiblePositionNumber;
    }

    public Pair(Parcel parcel) {
        this.categoryName = parcel.readString();
        this.firstVisiblePositionNumber = parcel.readInt();
    }

    public static final Creator<Pair> CREATOR = new Creator<Pair>() {
        @Override
        public Pair createFromParcel(Parcel parcel) {
            return new Pair(parcel);
        }

        @Override
        public Pair[] newArray(int i) {
            return new Pair[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.categoryName);
        parcel.writeInt(this.firstVisiblePositionNumber);
    }
}
