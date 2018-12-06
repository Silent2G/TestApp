package com.yleaf.stas.testapplication.di.components;

import com.yleaf.stas.testapplication.di.annotations.MainComponentScope;
import com.yleaf.stas.testapplication.di.modules.ApiServiceDataModule;
import com.yleaf.stas.testapplication.services.APIServiceData;

import dagger.Component;

@MainComponentScope
@Component(modules = {ApiServiceDataModule.class})
public interface MainComponent {
    APIServiceData getApiServiceData();
}
