package com.level42.mixit.activities;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.LightningTalk;
import com.level42.mixit.models.LightningTalkList;
import com.level42.mixit.services.ILightningTalkService;
import com.level42.mixit.services.adapters.LightningTalksAdapter;
import com.level42.mixit.tasks.GetLightningTalksAsyncTask;
import com.level42.mixit.utils.Utils;

/**
 * Activité principale de l'application
 */
@ContentView(R.layout.activity_lightningtalk_list)
public class LightningTalkListActivity extends RoboActivity implements Observer {

	@InjectView(R.id.listLightningTalks)
	private ListView listTalks;
	
	@Inject
	private ILightningTalkService talkService;
	
	private GetLightningTalksAsyncTask getLightningTalksAsyncService;
	
	/**
	 * Liste des talkls de l'activité
	 */
	private LightningTalkList talks = new LightningTalkList(); 

	/**
	 * Boite d'attente de chargement
	 */
	private ProgressDialog progressDialog;

	/**
	 * Adapter de la liste des talks
	 */
	private LightningTalksAdapter adapter;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        talks.addObserver(this);
		adapter = new LightningTalksAdapter(this.getBaseContext());
		listTalks.setAdapter(adapter);
		
		listTalks.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {				
				Intent talkActivity = new Intent(LightningTalkListActivity.this, LightningTalkActivity.class);
				talkActivity.putExtra(LightningTalkActivity.TALK_ID, id);
	            LightningTalkListActivity.this.startActivity(talkActivity);
			}
	    });
		
		@SuppressWarnings("unchecked")
		List<LightningTalk> savedTalks = (List<LightningTalk>) getLastNonConfigurationInstance();
        if (savedTalks == null) {
        	this.setupProgressDialog();
        	this.refreshTalks();  
        } else {
        	talks.setTalks(savedTalks);
        }
    }
    
    @Override
    public Object onRetainNonConfigurationInstance() {
        final List<LightningTalk> talks = this.talks.getTalks();
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
					LightningTalkListActivity.this,
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
    	getLightningTalksAsyncService = new GetLightningTalksAsyncTask();
		
    	// Ajout d'un listener pour récupérer le retour
    	getLightningTalksAsyncService.setPostExecuteListener(new OnTaskPostExecuteListener<List<LightningTalk>>() {			
			public void onTaskPostExecuteListener(List<LightningTalk> result) {
				if(result != null) {
					Log.d(Utils.LOGTAG, "Nombre de talks : " + result.size());
					talks.setTalks(result);
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
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
    	getLightningTalksAsyncService.execute(talkService);
    }
    
    /**
     * Méthode appelée lorsqu'un objet sur lequel l'activité est abonnée est mis à jour
     * 
     * @param observable Objet mis à jour
     * @param data Données mise à jour
     */
	public void update(Observable observable, Object data) {
		if (observable instanceof LightningTalkList) {
			Log.d(Utils.LOGTAG, "Changement sur la liste des lightning talks");
			LightningTalkList list = (LightningTalkList) observable;
			if(list != null) {
				adapter.updateTalks(list.getTalks());
			}
		}
	}
}

