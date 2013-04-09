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
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.Talk;
import com.level42.mixit.models.TalkList;
import com.level42.mixit.services.ITalkService;
import com.level42.mixit.services.adapters.TalksAdapter;
import com.level42.mixit.tasks.GetTalksAsyncTask;
import com.level42.mixit.utils.MessageBox;
import com.level42.mixit.utils.Utils;

/**
 * Activité de listage des talks.
 */
@ContentView(R.layout.activity_talk_list)
public class TalkListActivity extends RoboActivity implements Observer {

    /**
     * Contrôle : Liste des talks.
     */
    @InjectView(R.id.listTalks)
    private ListView listTalks;

    /**
     * Interface vers le service de gestion des talks.
     */
    @Inject
    private ITalkService talkService;

    /**
     * Liste des talks de l'activité.
     */
    private TalkList talks = new TalkList();

    /**
     * Boite d'attente de chargement.
     */
    private ProgressDialog progressDialog;

    /**
     * Adapter de la liste des talks.
     */
    private TalksAdapter adapter;

    /*
     * (non-Javadoc)
     * @see roboguice.activity.RoboActivity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        talks.addObserver(this);
        adapter = new TalksAdapter(this.getBaseContext());
        listTalks.setAdapter(adapter);

        listTalks.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Intent talkActivity = new Intent(TalkListActivity.this,
                        TalkActivity.class);
                talkActivity.putExtra(TalkActivity.TALK_ID, id);
                TalkListActivity.this.startActivity(talkActivity);
            }
        });

        @SuppressWarnings("unchecked")
        List<Talk> savedTalks = (List<Talk>) getLastNonConfigurationInstance();
        if (savedTalks == null) {
            this.setupProgressDialog();
            this.refreshTalksList();
        } else {
            talks.setTalks(savedTalks);
        }
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onRetainNonConfigurationInstance()
     */
    @Override
    public Object onRetainNonConfigurationInstance() {
        return this.talks.getTalks();
    }

    /**
     * Affiche la boite de chargement.
     */
    protected void setupProgressDialog() {
        if (progressDialog == null) {
            progressDialog = MessageBox
                    .getProgressDialog(TalkListActivity.this);
        }
    }

    /**
     * Rafraichit la liste des talks.
     */
    protected void refreshTalksList() {
        // Préparation du service
        GetTalksAsyncTask getTalksAsyncService = new GetTalksAsyncTask();

        // Ajout d'un listener pour récupérer le retour
        getTalksAsyncService
                .setPostExecuteListener(new OnTaskPostExecuteListener<List<Talk>>() {
                    public void onTaskPostExecuteListener(List<Talk> result) {
                        if (result != null) {
                            talks.setTalks(result);
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    }

                    public void onTaskInterruptListener(Exception cancelReason) {
                        OnClickListener listener = new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                finish();
                            }
                        };
                        MessageBox.showError(
                                getResources().getString(
                                        R.string.label_dialog_error),
                                cancelReason.getMessage(), listener,
                                TalkListActivity.this);
                    }

                    public void onTaskCancelledListener() {
                        MessageBox.showInformation(
                                getResources().getString(
                                        R.string.label_dialog_aborted),
                                TalkListActivity.this);
                    }
                });

        // Execution du service
        getTalksAsyncService.execute(talkService);
    }

    /**
     * Méthode appelée lorsqu'un objet sur lequel l'activité est abonnée est mis à jour.
     * @param observable Objet mis à jour
     * @param data Données mise à jour
     */
    public void update(Observable observable, Object data) {
        if (observable instanceof TalkList) {
            Log.d(Utils.LOGTAG, "Changement sur la liste des talks");
            TalkList list = (TalkList) observable;
            if (list != null) {
                adapter.updateTalks(list.getTalks());
            }
        }
    }
}
