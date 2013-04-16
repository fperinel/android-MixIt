package com.level42.mixit.adapters;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.level42.mixit.R;
import com.level42.mixit.models.LightningTalk;
import com.level42.mixit.models.Talk;
import com.level42.mixit.utils.Utils;

/**
 * Adapter pour la liste des lightning talks.
 */
public class LightningTalksAdapter extends BaseAdapter {

    /**
     * Liste des talks.
     */
    private List<LightningTalk> lightningTalk = Collections.emptyList();

    /**
     * Contexte de l'activité appelante.
     */
    private Context context;

    /**
     * Constructeur.
     * @param context Contexte de l'activité
     */
    public LightningTalksAdapter(Context context) {
        this.context = context;
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return (lightningTalk != null ? lightningTalk.size() : 0);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public LightningTalk getItem(int arg0) {
        return lightningTalk.get(arg0);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int arg0) {
        Talk talk = (Talk) this.getItem(arg0);
        if (talk != null) {
            return talk.getId();
        }
        return -1;
    }

    /**
     * Mise à jour des talks.
     * @param lightningTalk
     */
    public void updateTalks(List<LightningTalk> lightningTalk) {
        Utils.checkOnMainThread();
        this.lightningTalk = lightningTalk;
        notifyDataSetChanged();
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView txtTalkTitre;
        TextView txtTalkShortDescription;
        TextView txtTalkVote;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.lightningtalk_row, null);

            txtTalkTitre = (TextView) convertView
                    .findViewById(R.id.txtLTalkTitre);
            txtTalkShortDescription = (TextView) convertView
                    .findViewById(R.id.txtLTalkShortDescription);
            txtTalkVote = (TextView) convertView
                    .findViewById(R.id.txtLTalkVotes);
            
            convertView.setTag(R.id.txtLTalkTitre, txtTalkTitre);
            convertView.setTag(R.id.txtLTalkShortDescription, txtTalkShortDescription);
            convertView.setTag(R.id.txtLTalkVotes, txtTalkVote);
        } else {
            txtTalkTitre = (TextView) convertView.getTag(R.id.txtLTalkTitre);
            txtTalkShortDescription = (TextView) convertView.getTag(R.id.txtLTalkShortDescription);
            txtTalkVote = (TextView) convertView.getTag(R.id.txtLTalkVotes);
        }

        LightningTalk talk = getItem(position);
        txtTalkTitre.setText(talk.getTitle());
        txtTalkShortDescription.setText(talk.getSummary());
        txtTalkVote.setText(String.format(context.getResources().getString(R.string.label_talk_votes), talk.getNbVotes().toString()));
        return convertView;
    }

}
