package com.level42.mixit.activities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.Speaker;
import com.level42.mixit.models.Talk;
import com.level42.mixit.services.ITalkService;
import com.level42.mixit.tasks.GetTalkAsyncTask;
import com.level42.mixit.utils.Utils;

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
	
	@InjectView(R.id.talk_textInterets)
	private TextView interetsTalk;
	
	@InjectView(R.id.talk_textDate)
	private TextView dateTalk;
	
	@InjectView(R.id.talk_textSalle)
	private TextView salleTalk;
	
	@InjectView(R.id.talk_speakers)
	private LinearLayout speakersTalk;
	
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
    	GetTalkAsyncTask getTalkAsyncService = new GetTalkAsyncTask();
    	
    	// Ajout d'un listener pour récupérer le retour
    	getTalkAsyncService.setPostExecuteListener(new OnTaskPostExecuteListener<Talk>() {			
			public void onTaskPostExecuteListener(Talk result) {
				if(result != null) {
					Log.d(Utils.LOGTAG, "Talk chargé");
					talk = result;
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
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
		
		Resources res = getResources();
	
		titreTalk.setText(String.format(res.getString(R.string.label_talk_titre), talk.getFormat(), talk.getTitle()));
		contenuTalk.setText(talk.getDescription());
		
		// Ajout des speakers
		for (Speaker speaker : talk.getSpeakers() ) {			
			ImageView speakerAvatarView = new ImageView(TalkActivity.this);
			speakerAvatarView.setImageBitmap(speaker.getImage());
			speakersTalk.addView(speakerAvatarView);
			
			TextView speakerNameView = new TextView(TalkActivity.this);
			speakerNameView.setPadding(5, 10, 5, 10);
			speakerNameView.setMaxLines(2);
			speakerNameView.setMaxWidth(300);
			speakerNameView.setText(String.format(res.getString(R.string.label_talk_speaker), speaker.getFirstname(), speaker.getLastname()));			
			speakersTalk.addView(speakerNameView);
		}

		if (talk.getInterests() != null)
		{
			interetsTalk.setText(String.format(res.getString(R.string.label_talk_interets), String.valueOf(talk.getInterests().size())));
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

