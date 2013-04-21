package com.level42.mixit.activities;

import roboguice.activity.RoboPreferenceActivity;
import android.content.Intent;
import android.os.Bundle;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.webservices.IWebServices;

/**
 * Activité de configuration des préférences
 */
public class PreferencesActivity extends RoboPreferenceActivity {
    
    public static final String PREF_MEMBRE_LOGIN = "loginMembre";
    public static final String PREF_SESSION_HIDE = "hideSession";
    public static final String PREF_SESSION_DELAY = "delaySession";
    public static final String PREF_SESSION_NOTIFICATION = "notificationFavoris";
    public static final int REQUEST_CODE = 1;    
    
    @Inject
    private IWebServices ws;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_preference);
    }
    
    @Override
    public void finish() {
        
        // On vide le cache
        ws.cleanCache();
        
        // On recharge l'activité principale
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(RESULT_OK, intent);
        
        super.finish();
    }
}
