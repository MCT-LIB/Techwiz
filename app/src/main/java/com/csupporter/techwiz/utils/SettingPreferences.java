package com.csupporter.techwiz.utils;

import android.content.SharedPreferences;

import com.csupporter.techwiz.App;

public class SettingPreferences {

    ///////////////////////////////////////////////////////////////////////////
    // KEY PREF
    ///////////////////////////////////////////////////////////////////////////

    private static final String PREF_NAME = "setting_preferences";

    ///////////////////////////////////////////////////////////////////////////
    // INSTANCE
    ///////////////////////////////////////////////////////////////////////////

    private final SharedPreferences mSharedPreferences;

    private SettingPreferences() {
        mSharedPreferences = App.getApp().getSharedPreferences(PREF_NAME, 0);
    }

    private static class Holder {
        private static final SettingPreferences INSTANCE = new SettingPreferences();
    }

    public static SettingPreferences getInstance() {
        return Holder.INSTANCE;
    }

    private SharedPreferences.Editor editor() {
        return mSharedPreferences.edit();
    }

    ///////////////////////////////////////////////////////////////////////////
    // SETTING HERE
    ///////////////////////////////////////////////////////////////////////////

}
