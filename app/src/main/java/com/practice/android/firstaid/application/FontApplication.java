package com.practice.android.firstaid.application;

import com.practice.android.firstaid.Helper.FontsOverride;

/**
 * Created by parven on 22-10-2017.
 */

public final class FontApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "opensans-regular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "opensans-regular.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "opensans-regular.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "opensans-regular.ttf");
    }
}
