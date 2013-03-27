package com.level42.mixit.activities;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.GroupedTalks;
import com.level42.mixit.models.PlanningTalk;
import com.level42.mixit.services.IPlanningService;
import com.level42.mixit.services.adapters.PlanningAdapter;
import com.level42.mixit.tasks.GetPlanningAsyncTask;
import com.level42.mixit.utils.Utils;

/**
 * Activité principale de l'application
 */
@ContentView(R.layout.activity_planning)
public class PlanningActivity extends RoboActivity implements Observer {

	@InjectView(R.id.expandableListPlanning)
	private ExpandableListView listPlanning;
	
	@Inject
	private IPlanningService planningService;
	
	private GetPlanningAsyncTask getTalksAsyncService;
	
	/**
	 * Liste des talkls de l'activité
	 */
	private PlanningTalk talks = new PlanningTalk(); 

	/**
	 * Boite d'attente de chargement
	 */
	private ProgressDialog progressDialog;

	/**
	 * Adapter de la liste des talks
	 */
	private PlanningAdapter adapter;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        talks.addObserver(this);
		adapter = new PlanningAdapter(this.getBaseContext());
		listPlanning.setAdapter(adapter);
		
		listPlanning.setOnChildClickListener(new OnChildClickListener() {
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent talkActivity = new Intent(PlanningActivity.this, TalkActivity.class);
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
    
    @Override
    public Object onRetainNonConfigurationInstance() {
        final List<GroupedTalks> talks = this.talks.getGroupedTalks();
        return talks;
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    }

    /**
     * Affiche la boite de chargement
     */
    protected void setupProgressDialog() {
    	if(progressDialog == null) 
    	{
			progressDialog = ProgressDialog.show(
					PlanningActivity.this,
					null, //this.getText(R.string.loading_message),  
					this.getText(R.string.loading_message_talks), 
				    false, 
				    true);
    	}
    }
    
    /**
     * Rafraichit la liste des talks
     */
    protected void refreshTalks() {
    	// Préparation du service
    	getTalksAsyncService = new GetPlanningAsyncTask();
		
    	// Ajout d'un listener pour récupérer le retour
    	getTalksAsyncService.setPostExecuteListener(new OnTaskPostExecuteListener<List<GroupedTalks>>() {			
			public void onTaskPostExecuteListener(List<GroupedTalks> result) {
				if(result != null) {
					Log.d(Utils.LOGTAG, "Nombre de talks : " + result.size());
					talks.setGroupedTalks(result);
				} else {
					Log.d(Utils.LOGTAG, "Aucun planning");
				}
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
			}

			public void onTaskInterruptListener(Exception cancelReason) {
		        Builder builder = new AlertDialog.Builder(getApplicationContext());
		        builder.setMessage("Erreur")
		               .setCancelable(false)
		               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   finish();
		                   }
		               });
		        AlertDialog alertDialog = builder.create();
				alertDialog.setMessage(cancelReason.getMessage());
		        alertDialog.show();
			}

			public void onTaskCancelledListener() {
				Toast.makeText(getApplicationContext(), "Action annulée", Toast.LENGTH_SHORT).show();
			}
		});
    	
    	// Execution du service
    	getTalksAsyncService.execute(planningService);
    }
    
    /**
     * Méthode appelée lorsqu'un objet sur lequel l'activité est abonnée est mis à jour
     * 
     * @param observable Objet mis à jour
     * @param data Données mise à jour
     */
	public void update(Observable observable, Object data) {
		if (observable instanceof PlanningTalk) {
			Log.d(Utils.LOGTAG, "Changement sur le planning");
			PlanningTalk planning = (PlanningTalk) observable;
			if(planning != null) {
				adapter.updateTalks(planning.getGroupedTalks());
			}
		}
	}
}

