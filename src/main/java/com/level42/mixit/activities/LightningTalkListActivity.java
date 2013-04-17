package com.level42.mixit.activities;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.adapters.LightningTalksAdapter;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.LightningTalk;
import com.level42.mixit.models.LightningTalkList;
import com.level42.mixit.services.ILightningTalkService;
import com.level42.mixit.tasks.GetLightningTalksAsyncTask;
import com.level42.mixit.utils.MessageBox;
import com.level42.mixit.utils.Utils;

/**
 * Activité de listage des lightning talks.
 */
@ContentView(R.layout.activity_lightningtalk_list)
public class LightningTalkListActivity extends RoboActivity implements Observer {

    /**
     * Contrôle : Liste des talks.
     */
    @InjectView(R.id.listLightningTalks)
    private ListView listLightningTalks;

    /**
     * Interface vers le service de gestion des lightning talk.
     */
    @Inject
    private ILightningTalkService lightningTalkService;

    /**
     * Liste des talks de l'activité.
     */
    private LightningTalkList lightningTalks = new LightningTalkList();

    /**
     * Boite d'attente de chargement.
     */
    private ProgressDialog progressDialog;

    /**
     * Adapter de la liste des talks.
     */
    private LightningTalksAdapter adapter;

    /**
     * Listener sur la validation d'une erreur
     */
    private OnClickListener listenerFinish = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int which) {
            finish();
        }
    };
    
    /**
     * Listener pour la tâche asynchrone
     */
    private OnTaskPostExecuteListener<List<LightningTalk>> listenerAsync = new OnTaskPostExecuteListener<List<LightningTalk>>() {
        public void onTaskPostExecuteListener(List<LightningTalk> result) {
            if (result != null) {
                lightningTalks.setTalks(result);
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } catch (IllegalArgumentException ex) {
                    // nop
                }
            }
        }

        public void onTaskInterruptListener(Exception cancelReason) {
            MessageBox.showError(getResources().getString(R.string.label_dialog_error),
                    cancelReason.getMessage(), listenerFinish, LightningTalkListActivity.this);
        }

        public void onTaskCancelledListener() {
            MessageBox.showInformation(getResources().getString(R.string.label_dialog_aborted),
                    LightningTalkListActivity.this);
        }
    };
    
    /*
     * (non-Javadoc)
     * @see roboguice.activity.RoboActivity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lightningTalks.addObserver(this);
        adapter = new LightningTalksAdapter(this.getBaseContext());
        listLightningTalks.setAdapter(adapter);

        listLightningTalks.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Intent talkActivity = new Intent(
                        LightningTalkListActivity.this,
                        LightningTalkActivity.class);
                talkActivity.putExtra(LightningTalkActivity.TALK_ID, id);
                LightningTalkListActivity.this.startActivity(talkActivity);
            }
        });

        @SuppressWarnings("unchecked")
        List<LightningTalk> savedTalks = (List<LightningTalk>) getLastNonConfigurationInstance();
        if (savedTalks == null) {
            this.setupProgressDialog();
            this.refreshLightningTalks();
        } else {
            lightningTalks.setTalks(savedTalks);
        }
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onRetainNonConfigurationInstance()
     */
    @Override
    public Object onRetainNonConfigurationInstance() {
        return this.lightningTalks.getTalks();
    }

    /**
     * Affiche la boite de chargement
     */
    protected void setupProgressDialog() {
        if (progressDialog == null) {
            progressDialog = MessageBox
                    .getProgressDialog(LightningTalkListActivity.this);
        }
    }

    /**
     * Rafraichit la liste des talks.
     */
    protected void refreshLightningTalks() {
        // Préparation du service
        GetLightningTalksAsyncTask getLightningTalksAsyncService = new GetLightningTalksAsyncTask();

        // Ajout d'un listener pour récupérer le retour
        getLightningTalksAsyncService.setPostExecuteListener(listenerAsync);

        // Execution du service
        getLightningTalksAsyncService.execute(lightningTalkService);
    }

    /**
     * Méthode appelée lorsqu'un objet sur lequel l'activité est abonnée est mis à jour.
     * @param observable Objet mis à jour
     * @param data Données mise à jour
     */
    public void update(Observable observable, Object data) {
        if (observable instanceof LightningTalkList) {
            Log.d(Utils.LOGTAG, "Changement sur la liste des lightning talks");
            LightningTalkList list = (LightningTalkList) observable;
            if (list != null) {
                adapter.updateTalks(list.getTalks());
            }
        }
    }
}
