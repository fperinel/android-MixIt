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
import com.level42.mixit.models.Talk;
import com.level42.mixit.models.TalkList;
import com.level42.mixit.services.ITalkService;
import com.level42.mixit.services.adapters.TalksAdapter;
import com.level42.mixit.tasks.GetTalksAsyncTask;
import com.level42.mixit.utils.Utils;

/**
 * Activité principale de l'application
 */
@ContentView(R.layout.activity_talk_list)
public class TalkListActivity extends RoboActivity implements Observer {

	@InjectView(R.id.listTalks)
	private ListView listTalks;
	
	@Inject
	private ITalkService talkService;
	
	private GetTalksAsyncTask getTalksAsyncService;
	
	/**
	 * Liste des talkls de l'activité
	 */
	private TalkList talks = new TalkList(); 

	/**
	 * Boite d'attente de chargement
	 */
	private ProgressDialog progressDialog;

	/**
	 * Adapter de la liste des talks
	 */
	private TalksAdapter adapter;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        talks.addObserver(this);
		adapter = new TalksAdapter(this.getBaseContext());
		listTalks.setAdapter(adapter);
		
		listTalks.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {				
				Intent talkActivity = new Intent(TalkListActivity.this, TalkActivity.class);
				talkActivity.putExtra(TalkActivity.TALK_ID, id);
	            TalkListActivity.this.startActivity(talkActivity);
			}
	    });
		
		@SuppressWarnings("unchecked")
		List<Talk> savedTalks = (List<Talk>) getLastNonConfigurationInstance();
        if (savedTalks == null) {
        	this.setupProgressDialog();
        	this.refreshTalks();  
        } else {
        	talks.setTalks(savedTalks);
        }
    }
    
    @Override
    public Object onRetainNonConfigurationInstance() {
        final List<Talk> talks = this.talks.getTalks();
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
					TalkListActivity.this,
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
    	getTalksAsyncService = new GetTalksAsyncTask();
		
    	// Ajout d'un listener pour récupérer le retour
    	getTalksAsyncService.setPostExecuteListener(new OnTaskPostExecuteListener<List<Talk>>() {			
			public void onTaskPostExecuteListener(List<Talk> result) {
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
    	getTalksAsyncService.execute(talkService);
    }
    
    /**
     * Méthode appelée lorsqu'un objet sur lequel l'activité est abonnée est mis à jour
     * 
     * @param observable Objet mis à jour
     * @param data Données mise à jour
     */
	public void update(Observable observable, Object data) {
		if (observable instanceof TalkList) {
			Log.d(Utils.LOGTAG, "Changement sur la liste des talks");
			TalkList list = (TalkList) observable;
			if(list != null) {
				adapter.updateTalks(list.getTalks());
			}
		}
	}
}
