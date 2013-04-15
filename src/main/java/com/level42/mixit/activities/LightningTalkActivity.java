package com.level42.mixit.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
import com.level42.mixit.models.LightningTalk;
import com.level42.mixit.models.Speaker;
import com.level42.mixit.services.ILightningTalkService;
import com.level42.mixit.tasks.GetLightningTalkAsyncTask;
import com.level42.mixit.utils.MessageBox;

/**
 * Ecran de détail d'un lightning talk.
 */
@ContentView(R.layout.activity_lightningtalk)
public class LightningTalkActivity extends RoboActivity {

    /**
     * Identifiant du talk passé en paramètre de l'activité.
     */
    public static final String TALK_ID = "TALK_ID";

    /**
     * Interface vers le service de gestion des lightning talk.
     */
    @Inject
    private ILightningTalkService lightningTalkService;

    /**
     * Contrôle : Titre du talk.
     */
    @InjectView(R.id.ltalk_textTitre)
    private TextView titreTalk;

    /**
     * Contrôle : Contenu du talk.
     */
    @InjectView(R.id.ltalk_textContenu)
    private TextView contenuTalk;

    /**
     * Contrôle : Centre d'intérêts du talk.
     */
    @InjectView(R.id.ltalk_textInteret)
    private TextView interestsTalk;

    /**
     * Contrôle : Nombre de vote du talk.
     */
    @InjectView(R.id.ltalk_textVote)
    private TextView votesTalk;

    /**
     * Contrôle : Liste des speakers du talk.
     */
    @InjectView(R.id.ltalk_speakers)
    private LinearLayout speakersTalk;

    /**
     * Liste des talks de l'activité.
     */
    private LightningTalk lightningTalk = new LightningTalk();

    /**
     * Boite d'attente de chargement.
     */
    private ProgressDialog progressDialog;

    /**
     * Listener sur la validation d'une erreur
     */
    private OnClickListener listenerFinish = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int which) {
            finish();
        }
    };
    
    /**
     * Listener sur la tâche asynchrone
     */
    private OnTaskPostExecuteListener<LightningTalk> listenerAsync = new OnTaskPostExecuteListener<LightningTalk>() {
        public void onTaskPostExecuteListener(LightningTalk result) {
            if (result != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                displayTalk(result);
            }
        }

        public void onTaskInterruptListener(Exception cancelReason) {
            MessageBox.showError(getResources().getString(R.string.label_dialog_error), 
                    cancelReason.getMessage(), listenerFinish, LightningTalkActivity.this);
        }

        public void onTaskCancelledListener() {
            MessageBox.showInformation(getResources().getString(R.string.label_dialog_aborted),
                    LightningTalkActivity.this);
        }
    };
    
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
            this.displayTalk(savedTalk);
        }
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onRetainNonConfigurationInstance()
     */
    @Override
    public Object onRetainNonConfigurationInstance() {
        return this.lightningTalk;
    }

    /**
     * Affiche la boite de chargement.
     */
    protected void setupProgressDialog() {
        if (progressDialog == null) {
            progressDialog = MessageBox
                    .getProgressDialog(LightningTalkActivity.this);
        }
    }

    /**
     * Rafraichit la liste des talks.
     */
    protected void loadTalk() {
        // Préparation du service
        GetLightningTalkAsyncTask getTalkAsyncService = new GetLightningTalkAsyncTask();

        // Ajout d'un listener pour récupérer le retour
        getTalkAsyncService
                .setPostExecuteListener(listenerAsync);

        // Execution du service
        Integer id = (int) this.getIntent().getExtras()
                .getLong(LightningTalkActivity.TALK_ID);
        getTalkAsyncService.execute(lightningTalkService, id);
    }

    /**
     * Gère le rendu du talk dans le template.
     * @param talk Talk à afficher
     */
    protected void displayTalk(LightningTalk talk) {
        Resources res = getResources();
        
        lightningTalk = talk;

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
            List<String> list = new ArrayList<String>();
            for (Interest interest : talk.getInterests()) {
                list.add(interest.getName());
            }
            interestsTalk.setText(StringUtils.join(list, ", "));
        }

        if (talk.getNbVotes() != null) {
            votesTalk.setText(String.format(
                    res.getString(R.string.label_talk_votes),
                    String.valueOf(talk.getNbVotes())));
        }
    }
}
