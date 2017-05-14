package com.nabilla.iakforecast;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;


public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_setting);
    }
}
