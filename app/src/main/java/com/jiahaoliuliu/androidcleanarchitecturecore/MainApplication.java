package com.jiahaoliuliu.androidcleanarchitecturecore;

import android.app.Application;

public class MainApplication extends Application {

    private static Application application;
    private static MainComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mainComponent = DaggerMainComponent.builder()
//                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static Application getApplication() {
        return application;
    }

    public static MainComponent getMainComponent() {
        return mainComponent;
    }
}
