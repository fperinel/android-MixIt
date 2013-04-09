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
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.GroupedTalks;
import com.level42.mixit.models.PlanningTalk;
import com.level42.mixit.services.IPlanningService;
import com.level42.mixit.services.adapters.PlanningAdapter;
import com.level42.mixit.tasks.GetPlanningAsyncTask;
import com.level42.mixit.utils.MessageBox;
import com.level42.mixit.utils.Utils;

/**
 * Activité d'affichage du planning de l'événément MixIt.
 */
@ContentView(R.layout.activity_planning)
public class PlanningActivity extends RoboActivity implements Observer {

    /**
     * Contrôle : Liste des sessions.
     */
    @InjectView(R.id.expandableListPlanning)
    private ExpandableListView listPlanning;

    /**
     * Interface vers le service de gestion du planning.
     */
    @Inject
    private IPlanningService planningService;

    /**
     * Liste des talkls de l'activité.
     */
    private PlanningTalk talks = new PlanningTalk();

    /**
     * Boite d'attente de chargement.
     */
    private ProgressDialog progressDialog;

    /**
     * Adapter de la liste des talks.
     */
    private PlanningAdapter adapter;

    /**
     * Listener pour la tâche asynchrone
     */
    private OnTaskPostExecuteListener<List<GroupedTalks>> listener = new OnTaskPostExecuteListener<List<GroupedTalks>>() {
        public void onTaskPostExecuteListener(
                List<GroupedTalks> result) {
            if (result != null) {
                talks.setGroupedTalks(result);
            }
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
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
                    PlanningActivity.this);
        }

        public void onTaskCancelledListener() {
            MessageBox.showInformation(
                    getResources().getString(
                            R.string.label_dialog_aborted),
                    PlanningActivity.this);
        }
    };
    
    /*
     * (non-Javadoc)
     * @see roboguice.activity.RoboActivity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        talks.addObserver(this);
        adapter = new PlanningAdapter(this.getBaseContext());
        listPlanning.setAdapter(adapter);

        listPlanning.setOnChildClickListener(new OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                Intent talkActivity = new Intent(PlanningActivity.this,
                        TalkActivity.class);
                talkActivity.putExtra(TalkActivity.TALK_ID, id);
                PlanningActivity.this.startActivity(talkActivity);
                return true;
            }
        });

        @SuppressWarnings("unchecked")
        List<GroupedTalks> savedTalks = (List<GroupedTalks>) getLastNonConfigurationInstance();
        if (savedTalks == null) {
            this.setupProgressDialog();
            this.refreshTalks();
        } else {
            talks.setGroupedTalks(savedTalks);
        }
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onRetainNonConfigurationInstance()
     */
    @Override
    public Object onRetainNonConfigurationInstance() {
        return this.talks.getGroupedTalks();
    }

    /**
     * Affiche la boite de chargement.
     */
    protected void setupProgressDialog() {
        if (progressDialog == null) {
            progressDialog = MessageBox
                    .getProgressDialog(PlanningActivity.this);
        }
    }

    /**
     * Rafraichit la liste des sessions.
     */
    protected void refreshTalks() {
        // Préparation du service
        GetPlanningAsyncTask getTalksAsyncService = new GetPlanningAsyncTask();

        // Ajout d'un listener pour récupérer le retour
        getTalksAsyncService.setPostExecuteListener(listener);

        // Execution du service
        Integer delay = Integer.valueOf(getString(R.string.planningDelay));
        getTalksAsyncService.execute(planningService, delay);
    }

    /**
     * Méthode appelée lorsqu'un objet sur lequel l'activité est abonnée est mis à jour.
     * @param observable Objet mis à jour
     * @param data Données mise à jour
     */
    public void update(Observable observable, Object data) {
        if (observable instanceof PlanningTalk) {
            Log.d(Utils.LOGTAG, "Changement sur le planning");
            PlanningTalk planning = (PlanningTalk) observable;
            if (planning != null) {
                adapter.updateTalks(planning.getGroupedTalks());
            }
        }
    }
}
