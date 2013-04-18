package com.level42.mixit.activities;

import roboguice.activity.RoboPreferenceActivity;
import android.os.Bundle;

import com.level42.mixit.R;

/**
 * Activité de configuration des préférences
 */
public class PreferencesActivity extends RoboPreferenceActivity {
    
    public static final String PREF_MEMBRE_LOGIN = "loginMembre";
    public static final String PREF_SESSION_HIDE = "hideSession";
    public static final String PREF_SESSION_DELAY = "delaySession";
    public static final String PREF_SESSION_NOTIFICATION = "notificationFavoris";    
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_preference);
    }
}
