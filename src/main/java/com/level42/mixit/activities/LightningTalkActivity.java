package com.level42.mixit.activities;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.Interest;
import com.level42.mixit.models.LightningTalk;
import com.level42.mixit.models.Speaker;
import com.level42.mixit.services.ILightningTalkService;
import com.level42.mixit.tasks.GetLightningTalkAsyncTask;
import com.level42.mixit.utils.MessageBox;
import com.level42.mixit.utils.Utils;

/**
 * Ecran de détail d'un lightning talk
 */
@ContentView(R.layout.activity_talk)
public class LightningTalkActivity extends RoboActivity {

    /**
     * Identifiant du talk passé en paramètre de l'activité
     */
    public final static String TALK_ID = "TALK_ID";

    /**
     * Interface vers le service de gestion des lightning talk
     */
    @Inject
    private ILightningTalkService talkService;

    /**
     * Contrôle : Titre du talk
     */
    @InjectView(R.id.talk_textTitre)
    private TextView titreTalk;

    /**
     * Contrôle : Contenu du talk
     */
    @InjectView(R.id.talk_textContenu)
    private TextView contenuTalk;

    /**
     * Contrôle : Centre d'intérêts du talk
     */
    @InjectView(R.id.talk_layout_interests)
    private LinearLayout interestsLayoutTalk;

    /**
     * Contrôle : Nombre de vote du talk
     */
    @InjectView(R.id.talk_textVotes)
    private TextView votesTalk;

    /**
     * Contrôle : Liste des speakers du talk
     */
    @InjectView(R.id.talk_speakers)
    private LinearLayout speakersTalk;

    /**
     * Tâche asynchrone pour collecter les informations du talk
     */
    private GetLightningTalkAsyncTask getTalkAsyncService;

    /**
     * Liste des talks de l'activité
     */
    private LightningTalk talk = new LightningTalk();

    /**
     * Boite d'attente de chargement
     */
    private ProgressDialog progressDialog;

    /*
     * (non-Javadoc)
     * @see roboguice.activity.RoboActivity#onCreate(android.os.Bundle)
     */
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

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onRetainNonConfigurationInstance()
     */
    @Override
    public Object onRetainNonConfigurationInstance() {
	final LightningTalk talk = this.talk;
	return talk;
    }

    /*
     * (non-Javadoc)
     * @see roboguice.activity.RoboActivity#onResume()
     */
    @Override
    protected void onResume() {
	super.onResume();
    }

    /**
     * Affiche la boite de chargement
     */
    protected void setupProgressDialog() {
	if (progressDialog == null) {
	    progressDialog = MessageBox
		    .getProgressDialog(LightningTalkActivity.this);
	}
    }

    /**
     * Rafraichit la liste des talks
     */
    protected void loadTalk() {
	// Préparation du service
	getTalkAsyncService = new GetLightningTalkAsyncTask();

	// Ajout d'un listener pour récupérer le retour
	getTalkAsyncService
		.setPostExecuteListener(new OnTaskPostExecuteListener<LightningTalk>() {
		    public void onTaskPostExecuteListener(LightningTalk result) {
			if (result != null) {
			    Log.d(Utils.LOGTAG, "LightningTalk chargé");
			    talk = result;
			    if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			    }
			    displayTalk(talk);
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
				LightningTalkActivity.this);
		    }

		    public void onTaskCancelledListener() {
			MessageBox.showInformation(
				getResources().getString(
					R.string.label_dialog_aborted),
				LightningTalkActivity.this);
		    }
		});

	// Execution du service
	Integer id = (int) this.getIntent().getExtras()
		.getLong(LightningTalkActivity.TALK_ID);
	getTalkAsyncService.execute(talkService, id);
    }

    /**
     * Gère le rendu du talk dans le template
     * 
     * @param talk Talk à afficher
     */
    protected void displayTalk(LightningTalk talk) {
	Resources res = getResources();

	titreTalk.setText(talk.getTitle());
	contenuTalk.setText(talk.getDescription());

	// Ajout des speakers
	for (Speaker speaker : talk.getSpeakers()) {
	    ImageView speakerAvatarView = new ImageView(
		    LightningTalkActivity.this);
	    speakerAvatarView.setImageBitmap(speaker.getImage());
	    speakersTalk.addView(speakerAvatarView);

	    TextView speakerNameView = new TextView(LightningTalkActivity.this);
	    speakerNameView.setPadding(5, 10, 5, 10);
	    speakerNameView.setMaxLines(2);
	    speakerNameView.setMaxWidth(300);
	    speakerNameView.setText(String.format(
		    res.getString(R.string.label_talk_speaker),
		    speaker.getFirstname(), speaker.getLastname()));
	    speakersTalk.addView(speakerNameView);
	}

	if (talk.getInterests() != null) {
	    for (Interest interest : talk.getInterests()) {
		TextView interestView = new TextView(LightningTalkActivity.this);
		interestView.setMaxLines(5);
		interestView.setPadding(5, 2, 5, 2);
		interestView.setTextSize(12);
		interestView.setTextColor(getResources().getColor(
			R.color.mixitBlue));
		interestView.setText(interest.getName());
		interestsLayoutTalk.addView(interestView);
	    }
	}

	if (talk.getNbVotes() != null) {
	    votesTalk.setText(String.format(
		    res.getString(R.string.label_talk_votes),
		    String.valueOf(talk.getNbVotes())));
	}
    }
}
