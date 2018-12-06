package com.yleaf.stas.testapplication.api.model.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Head<T> {

    @SerializedName("args")
    @Expose
    private T t;

    public Head() {}

    public Head(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}