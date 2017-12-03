package com.practice.android.firstaid.application;

import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.FirebaseDatabase;
import com.practice.android.firstaid.Helper.FontsOverride;

import io.fabric.sdk.android.Fabric;

/**
 * Created by parven on 22-10-2017.
 */

public final class FontApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

        FontsOverride.setDefaultFont(this, "DEFAULT", "opensans-regular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "opensans-regular.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "opensans-regular.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "opensans-regular.ttf");

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Fabric.with(this, new Crashlytics());


    }
}
