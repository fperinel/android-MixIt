package com.level42.mixit.activities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.Interest;
import com.level42.mixit.models.Speaker;
import com.level42.mixit.models.Talk;
import com.level42.mixit.services.ITalkService;
import com.level42.mixit.tasks.GetTalkAsyncTask;
import com.level42.mixit.utils.MessageBox;

/**
 * Ecran de détail d'un talk.
 */
@ContentView(R.layout.activity_talk)
public class TalkActivity extends RoboActivity {

    /**
     * Identifiant du talk passé en paramètre de l'activité.
     */
    public static final String TALK_ID = "TALK_ID";

    /**
     * Interface vers le service de gestion des talks.
     */
    @Inject
    private ITalkService talkService;

    /**
     * Contrôle : Titre du talk.
     */
    @InjectView(R.id.talk_textTitre)
    private TextView titreTalk;

    /**
     * Contrôle : Contenu du talk.
     */
    @InjectView(R.id.talk_textContenu)
    private TextView contenuTalk;

    /**
     * Contrôle : Centre d'intérêts du talk.
     */
    @InjectView(R.id.talk_layout_interests)
    private LinearLayout interestsLayoutTalk;

    /**
     * Contrôle : Date du talk.
     */
    @InjectView(R.id.talk_textDate)
    private TextView dateTalk;

    /**
     * Contrôle : Salle du talk.
     */
    @InjectView(R.id.talk_textSalle)
    private TextView salleTalk;

    /**
     * Contrôle : Niveau du talk.
     */
    @InjectView(R.id.talk_textNiveau)
    private TextView niveauTalk;

    /**
     * Contrôle : Liste des speakers du talk.
     */
    @InjectView(R.id.talk_speakers)
    private LinearLayout speakersLayoutTalk;

    /**
     * Liste des talkls de l'activité.
     */
    private Talk talk = new Talk();

    /**
     * Boite d'attente de chargement.
     */
    private ProgressDialog progressDialog;

    /**
     * Listener sur la tâche asynchrone
     */
    private OnTaskPostExecuteListener<Talk> listenerAsync = new OnTaskPostExecuteListener<Talk>() {
        public void onTaskPostExecuteListener(Talk result) {
            if (result != null) {
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
                    TalkActivity.this);
        }

        public void onTaskCancelledListener() {
            MessageBox.showInformation(
                    getResources().getString(
                            R.string.label_dialog_aborted),
                    TalkActivity.this);
        }
    };
    
    /*
     * (non-Javadoc)
     * @see roboguice.activity.RoboActivity#onCreate(android.os.Bundle)
     */
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

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onRetainNonConfigurationInstance()
     */
    @Override
    public Object onRetainNonConfigurationInstance() {
        return this.talk;
    }

    /**
     * Affiche la boite de chargement.
     */
    protected void setupProgressDialog() {
        if (progressDialog == null) {
            progressDialog = MessageBox.getProgressDialog(TalkActivity.this);
        }
    }

    /**
     * Rafraichit la liste des talks.
     */
    protected void loadTalk() {

        // Préparation du service
        GetTalkAsyncTask getTalkAsyncService = new GetTalkAsyncTask();

        // Ajout d'un listener pour récupérer le retour
        getTalkAsyncService.setPostExecuteListener(listenerAsync);

        // Execution du service
        Integer id = (int) this.getIntent().getExtras()
                .getLong(TalkActivity.TALK_ID);
        getTalkAsyncService.execute(talkService, id);
    }

    /**
     * Gère le rendu du talk dans le template.
     * @param talk Talk a afficher
     */
    protected void displayTalk(Talk talk) {

        Resources res = getResources();

        titreTalk.setText(String.format(
                res.getString(R.string.label_talk_titre), talk.getFormat(),
                talk.getTitle()));
        contenuTalk.setText(talk.getDescription());

        // Ajout des speakers
        for (Speaker speaker : talk.getSpeakers()) {
            ImageView speakerAvatarView = new ImageView(TalkActivity.this);
            speakerAvatarView.setImageBitmap(speaker.getImage());
            speakersLayoutTalk.addView(speakerAvatarView);

            TextView speakerNameView = new TextView(TalkActivity.this);
            speakerNameView.setPadding(5, 10, 5, 10);
            speakerNameView.setMaxLines(2);
            speakerNameView.setMaxWidth(300);
            speakerNameView.setText(String.format(
                    res.getString(R.string.label_talk_speaker),
                    speaker.getFirstname(), speaker.getLastname()));
            speakersLayoutTalk.addView(speakerNameView);
        }

        if (talk.getInterests() != null) {
            for (Interest interest : talk.getInterests()) {
                TextView interestView = new TextView(TalkActivity.this);
                interestView.setMaxLines(5);
                interestView.setPadding(5, 2, 5, 2);
                interestView.setTextSize(12);
                interestView.setTextColor(getResources().getColor(
                        R.color.mixitBlue));
                interestView.setText(interest.getName());
                interestsLayoutTalk.addView(interestView);
            }
        }

        if (talk.getLevel() != null) {
            String niveau = (String) getResources().getText(
                    getResources().getIdentifier(
                            "label_talk_" + talk.getLevel(), "string",
                            "com.level42.mixit"));
            niveauTalk.setText(String.format(
                    res.getString(R.string.label_talk_niveau), niveau));
        }

        if (talk.getSalleSession() != null) {
            salleTalk.setText(String.format(
                    res.getString(R.string.label_talk_salle),
                    talk.getSalleSession()));
        }

        if (talk.getDateSession() != null) {
            DateFormat format = SimpleDateFormat.getDateTimeInstance(
                    SimpleDateFormat.MEDIUM, SimpleDateFormat.SHORT);
            dateTalk.setText(String.format(
                    res.getString(R.string.label_talk_date),
                    format.format(talk.getDateSession())));
        }
    }
}
