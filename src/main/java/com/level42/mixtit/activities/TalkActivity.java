package com.level42.mixtit.activities;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.inject.Inject;
import com.level42.mixtit.R;
import com.level42.mixtit.listeners.OnTaskPostExecuteListener;
import com.level42.mixtit.models.Talk;
import com.level42.mixtit.services.ITalkService;
import com.level42.mixtit.tasks.GetTalkAsyncTask;
import com.level42.mixtit.utils.Utils;

/**
 * Ecran de détail d'un talk
 */
@ContentView(R.layout.activity_talk)
public class TalkActivity extends RoboActivity {

	public final static String TALK_ID = "TALK_ID";
	
	@Inject
	private ITalkService talkService;
	
	@InjectView(R.id.talk_textTitre)
	private TextView titreTalk;
	
	@InjectView(R.id.talk_textContenu)
	private TextView contenuTalk;
	
	@InjectView(R.id.talk_textFavoris)
	private TextView favorisTalk;
	
	private GetTalkAsyncTask getTalkAsyncService;
	
	/**
	 * Liste des talkls de l'activité
	 */
	private Talk talk = new Talk();

	/**
	 * Boite d'attente de chargement
	 */
	private ProgressDialog progressDialog;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        		
		Talk savedTalk = (Talk) getLastNonConfigurationInstance();
        if (savedTalk == null) {
        	this.setupProgressDialog();
        	this.loadTalk();  
        } else {
        	talk = savedTalk;
            this.displayTalk(talk);
        }
    }
    
    @Override
    public Object onRetainNonConfigurationInstance() {
        final Talk talk = this.talk;
        return talk;
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
					TalkActivity.this, 
					this.getText(R.string.loading_message),  
					this.getText(R.string.loading_message_talk), 
				    false, 
				    false);
    	}
    }
    
    /**
     * Rafraichit la liste des talks
     */
    protected void loadTalk() {
    	// Préparation du service
    	getTalkAsyncService = new GetTalkAsyncTask();
    	
    	// Ajout d'un listener pour récupérer le retour
    	getTalkAsyncService.setPostExecuteListener(new OnTaskPostExecuteListener<Talk>() {			
			public void onTaskPostExecuteListener(Talk result) {
				if(result != null) {
					Log.d(Utils.LOGTAG, "Talk chargé");
					talk = result;
					if (progressDialog.isShowing()) {
						progressDialog.hide();
					}
					displayTalk(talk);
				}
			}
		});
    	
    	// Execution du service
    	Integer id = (int) this.getIntent().getExtras().getLong(TalkActivity.TALK_ID);
    	Log.d(Utils.LOGTAG, "Chargement du talk " + id.toString());
    	getTalkAsyncService.execute(talkService, id);
    }
	
	/**
	 * Gère le rendu du talk dans le template
	 * @param talk
	 */
	protected void displayTalk(Talk talk) {
		titreTalk.setText(talk.getTitle());
		contenuTalk.setText(talk.getDescription());
		if (talk.getInterests() != null)
		{
			favorisTalk.setText(String.valueOf(talk.getInterests().size()));
		}
	}
}

