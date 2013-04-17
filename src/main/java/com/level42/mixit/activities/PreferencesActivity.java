package com.level42.mixit.activities;

import roboguice.activity.RoboPreferenceActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.Member;
import com.level42.mixit.services.IEntityService;
import com.level42.mixit.tasks.GetMemberIdAsyncTask;
import com.level42.mixit.utils.MessageBox;

/**
 * Activité de configuration des préférences
 */
public class PreferencesActivity extends RoboPreferenceActivity {
    
    public static final String PREF_MEMBRE_LOGIN = "loginMembre";
    public static final String PREF_MEMBRE_ID = "idMembre";
    public static final String PREF_SESSION_HIDE = "hideSession";
    public static final String PREF_SESSION_DELAY = "delaySession";
    public static final String PREF_SESSION_NOTIFICATION = "notificationFavoris";    
    
    @Inject
    private IEntityService entityService;

    /**
     * Boite d'attente de chargement.
     */
    private ProgressDialog progressDialog;
    
    private boolean searchId = true;
    
    /**
     * Listener pour la tâche asynchrone
     */
    private OnTaskPostExecuteListener<Member> listenerAsync = new OnTaskPostExecuteListener<Member>() {
        public void onTaskPostExecuteListener(Member result) {
            if (result != null) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PreferencesActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(PreferencesActivity.PREF_MEMBRE_ID, result.getId());
                editor.commit();
                
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } catch (IllegalArgumentException ex) {
                    // nop
                }

                searchId = false;
                finish();
            }
        }

        public void onTaskInterruptListener(Exception cancelReason) {
           OnClickListener listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                        int which) {
                    searchId = false;
                    finish();
                }
           };
           MessageBox.showError(
                    getResources().getString(
                            R.string.label_dialog_error),
                    cancelReason.getMessage(), listener,
                    PreferencesActivity.this);
        }

        public void onTaskCancelledListener() {
            MessageBox.showInformation(
                    getResources().getString(
                            R.string.label_dialog_aborted),
                            PreferencesActivity.this);

            searchId = false;
            finish();
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_preference);
    }
    
    @Override
    public void finish() {
        
        if (searchId) {
            if (progressDialog == null) {
                progressDialog = MessageBox
                        .getProgressDialog(PreferencesActivity.this);
            }
            
            // Avant de terminer, on utilise l'adresse email pour récupérer l'identifiant du membre
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PreferencesActivity.this);
            String login = preferences.getString(PreferencesActivity.PREF_MEMBRE_LOGIN, null);
            
            if (login != null) {
                GetMemberIdAsyncTask asyncService = new GetMemberIdAsyncTask();
                asyncService.setPostExecuteListener(listenerAsync);
                asyncService.execute(entityService, login);
            }
        } else {
            super.finish();
        }
    }

}
