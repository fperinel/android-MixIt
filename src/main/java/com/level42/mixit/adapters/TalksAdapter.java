package com.level42.mixit.adapters;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.level42.mixit.R;
import com.level42.mixit.models.Talk;
import com.level42.mixit.utils.Utils;

/**
 * Adapter pour la liste des lightning talks.
 */
public class TalksAdapter extends BaseAdapter {

    /**
     * Liste des talks.
     */
    private List<Talk> talks = Collections.emptyList();

    /**
     * Contexte de l'activité appelante.
     */
    private Context context;

    /**
     * Constructeur.
     * @param context Contexte de l'activité
     */
    public TalksAdapter(Context context) {
        this.context = context;
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return (talks != null ? talks.size() : 0);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Talk getItem(int arg0) {
        return talks.get(arg0);
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
     * @param talks
     */
    public void updateTalks(List<Talk> talks) {
        Utils.checkOnMainThread();
        this.talks = talks;
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
        ImageView imgTalkFavoris;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.talk_row, null);

            txtTalkTitre = (TextView) convertView
                    .findViewById(R.id.txtTalkTitre);
            txtTalkShortDescription = (TextView) convertView
                    .findViewById(R.id.txtTalkShortDescription);
            imgTalkFavoris = (ImageView) convertView
                    .findViewById(R.id.imgTalkFavoris);

            convertView.setTag(R.id.txtTalkTitre, txtTalkTitre);
            convertView.setTag(R.id.txtTalkShortDescription,
                    txtTalkShortDescription);
            convertView.setTag(R.id.imgTalkFavoris,
                    imgTalkFavoris);
        } else {
            txtTalkTitre = (TextView) convertView.getTag(R.id.txtTalkTitre);
            txtTalkShortDescription = (TextView) convertView
                    .getTag(R.id.txtTalkShortDescription);
            imgTalkFavoris = (ImageView) convertView
                    .getTag(R.id.imgTalkFavoris);
        }

        Talk talk = getItem(position);
        txtTalkTitre.setText(talk.getTitle());
        txtTalkShortDescription.setText(talk.getSummary());
        
        if(!talk.isFavoris()) {
            imgTalkFavoris.setVisibility(View.INVISIBLE);
        } else {
            imgTalkFavoris.setVisibility(View.VISIBLE);            
        }
        
        return convertView;
    }

}
