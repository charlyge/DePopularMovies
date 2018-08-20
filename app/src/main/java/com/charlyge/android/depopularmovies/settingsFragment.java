package com.charlyge.android.depopularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by DELL PC on 8/3/2018.
 */

public class settingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_movies);
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();
        for (int i = 0 ;i<count;i++){
            Preference p = preferenceScreen.getPreference(i);
            if(p instanceof ListPreference){
                ListPreference listPreference = (ListPreference)p;
                String value = sharedPreferences.getString(p.getKey()," ");
                int index = listPreference.findIndexOfValue(value);
                listPreference.setSummary(listPreference.getEntries()[index]);
            }
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference p = findPreference(s);
        if(p instanceof ListPreference){
            ListPreference listPreference = (ListPreference)p;
            String value = sharedPreferences.getString(p.getKey()," ");
            int index = listPreference.findIndexOfValue(value);
            listPreference.setSummary(listPreference.getEntries()[index]);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
