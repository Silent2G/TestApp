package com.yleaf.stas.testapplication.api.networking.core;

import com.yleaf.stas.testapplication.api.networking.error.NetworkError;

public abstract class Service<T> {
    protected final T networkService;

    public Service(T networkService) {
        this.networkService = networkService;
    }

    public interface Callback<T> {
        void onSuccess(T t);
        void onError(NetworkError networkError);
    }
}