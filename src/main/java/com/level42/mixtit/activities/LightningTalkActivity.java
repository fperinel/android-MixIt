package com.level42.mixtit.activities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.inject.Inject;
import com.level42.mixtit.R;
import com.level42.mixtit.listeners.OnTaskPostExecuteListener;
import com.level42.mixtit.models.LightningTalk;
import com.level42.mixtit.models.Talk;
import com.level42.mixtit.services.ILightningTalkService;
import com.level42.mixtit.tasks.GetLightningTalkAsyncTask;
import com.level42.mixtit.utils.Utils;

/**
 * Ecran de détail d'un talk
 */
@ContentView(R.layout.activity_talk)
public class LightningTalkActivity extends RoboActivity {

	public final static String TALK_ID = "TALK_ID";
	
	@Inject
	private ILightningTalkService talkService;
	
	@InjectView(R.id.talk_textTitre)
	private TextView titreTalk;
	
	@InjectView(R.id.talk_textContenu)
	private TextView contenuTalk;
	
	@InjectView(R.id.talk_textFavoris)
	private TextView favorisTalk;
	
	@InjectView(R.id.talk_textDate)
	private TextView dateTalk;
	
	@InjectView(R.id.talk_textSalle)
	private TextView salleTalk;
	
	private GetLightningTalkAsyncTask getTalkAsyncService;
	
	/**
	 * Liste des talkls de l'activité
	 */
	private LightningTalk talk = new LightningTalk();

	/**
	 * Boite d'attente de chargement
	 */
	private ProgressDialog progressDialog;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        		
        LightningTalk savedTalk = (LightningTalk) getLastNonConfigurationInstance();
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
        final LightningTalk talk = this.talk;
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
					LightningTalkActivity.this,
					null, //this.getText(R.string.loading_message),  
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
    	getTalkAsyncService = new GetLightningTalkAsyncTask();
    	
    	// Ajout d'un listener pour récupérer le retour
    	getTalkAsyncService.setPostExecuteListener(new OnTaskPostExecuteListener<LightningTalk>() {			
			public void onTaskPostExecuteListener(LightningTalk result) {
				if(result != null) {
					Log.d(Utils.LOGTAG, "LightningTalk chargé");
					talk = result;
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					displayTalk(talk);
				}
			}
		});
    	
    	// Execution du service
    	Integer id = (int) this.getIntent().getExtras().getLong(LightningTalkActivity.TALK_ID);
    	Log.d(Utils.LOGTAG, "Chargement du LightningTalk " + id.toString());
    	getTalkAsyncService.execute(talkService, id);
    }
	
	/**
	 * Gère le rendu du talk dans le template
	 * @param talk
	 */
	protected void displayTalk(Talk talk) {
		titreTalk.setText(talk.getTitle());
		contenuTalk.setText(talk.getDescription());

		Resources res = getResources();
		
		if (talk.getInterests() != null)
		{
			favorisTalk.setText(String.format(res.getString(R.string.label_talk_favoris), String.valueOf(talk.getInterests().size())));
		}
		
		if (talk.getSalleSession() != null)
		{
			salleTalk.setText(String.format(res.getString(R.string.label_talk_salle), talk.getSalleSession()));
		}
		
		if (talk.getDateSession() != null)
		{
			DateFormat format = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MEDIUM, SimpleDateFormat.SHORT);
			dateTalk.setText(String.format(res.getString(R.string.label_talk_date), format.format(talk.getDateSession())));
		}
	}
}

