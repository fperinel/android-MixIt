package com.level42.mixit.activities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
    
    /**
     * Stockage du login avant modification
     */
    private String login;
    
    @Inject
    private IWebServices ws;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_preference);
        login = getPreferenceManager().getSharedPreferences().getString(PREF_MEMBRE_LOGIN, null);
    }
    
    @Override
    public void finish() {
        
        String newLogin = getPreferenceManager().getSharedPreferences().getString(PREF_MEMBRE_LOGIN, null);
        
        if(newLogin != null && !newLogin.equals(login)) {
            try {
                ws.cleanCache("talks");
                if (login != null) {
                    ws.cleanCache("members/" + URLEncoder.encode(login, "UTF-8") + "/favorites");
                }
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                setResult(RESULT_OK, intent);
            } catch (UnsupportedEncodingException e) {
                //
            }
        }
        
        super.finish();
    }
}
