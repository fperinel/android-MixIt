package com.level42.mixit.adapters;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.level42.mixit.R;
import com.level42.mixit.models.Talk;
import com.level42.mixit.utils.Utils;

/**
 * Adapter pour le planning.
 */
public class PlanningAdapter extends BaseAdapter {

    /**
     * Liste des talks.
     */
    private List<Talk> talks = Collections.emptyList();

    /**
     * Contexte de l'activité parente.
     */
    private Context context;

    /**
     * Constructeur de l'adaptateur.
     * @param lives
     */
    public PlanningAdapter(Context context) {
        this.context = context;
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

    @Override
    public int getCount() {
        return talks.size();
    }

    @Override
    public Talk getItem(int position) {
        return talks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return talks.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView txtTalkTitre;
        TextView txtTalkShortDescription;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.talk_row, null);

            txtTalkTitre = (TextView) convertView
                    .findViewById(R.id.txtTalkTitre);
            txtTalkShortDescription = (TextView) convertView
                    .findViewById(R.id.txtTalkShortDescription);

            convertView.setTag(R.id.txtTalkTitre, txtTalkTitre);
            convertView.setTag(R.id.txtTalkShortDescription,
                    txtTalkShortDescription);
        } else {
            txtTalkTitre = (TextView) convertView.getTag(R.id.txtTalkTitre);
            txtTalkShortDescription = (TextView) convertView
                    .getTag(R.id.txtTalkShortDescription);
        }

        Talk talk = getItem(position);
        txtTalkTitre.setText(talk.getTitle());
        txtTalkShortDescription.setText(talk.getSummary());

        return convertView;
    } 
}
