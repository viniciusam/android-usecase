package com.viniciusam.usecase;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Vinicius on 18/08/2016.
 */
public class RealmApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Create the default realm configuration.
        RealmConfiguration defaultConfig = new RealmConfiguration.Builder(this)
                .name("app.realm")
                .schemaVersion(1)
                .migration(new Migration())
                .build();
        Realm.setDefaultConfiguration(defaultConfig);
    }

}
