package com.level42.mixit.services.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.level42.mixit.R;
import com.level42.mixit.models.GroupedTalks;
import com.level42.mixit.models.Talk;
import com.level42.mixit.utils.Utils;

/**
 * Adapter pour le planning.
 */
public class PlanningAdapter extends BaseExpandableListAdapter {

    /**
     * Liste des talks.
     */
    private List<GroupedTalks> talks = Collections.emptyList();

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
    public void updateTalks(List<GroupedTalks> talks) {
        Utils.checkOnMainThread();
        this.talks = talks;
        notifyDataSetChanged();
    }

    /*
     * (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChild(int, int)
     */
    public Talk getChild(int groupPosition, int childPosition) {
        return talks.get(groupPosition).getTalks().get(childPosition);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChildId(int, int)
     */
    public long getChildId(int groupPosition, int childPosition) {
        return talks.get(groupPosition).getTalks().get(childPosition).getId();
    }

    /*
     * (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
     */
    public int getChildrenCount(int groupPosition) {
        return talks.get(groupPosition).getTalks().size();
    }

    /*
     * (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroup(int)
     */
    public GroupedTalks getGroup(int groupPosition) {
        return talks.get(groupPosition);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroupCount()
     */
    public int getGroupCount() {
        return talks.size();
    }

    /*
     * (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroupId(int)
     */
    public long getGroupId(int groupPosition) {
        return talks.get(groupPosition).getId();
    }

    /*
     * (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#hasStableIds()
     */
    public boolean hasStableIds() {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
     */
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
     */
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {

        TextView txtTalkDate;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.planning_header, null);
            txtTalkDate = (TextView) convertView
                    .findViewById(R.id.txtPlanningDate);
            convertView.setTag(R.id.txtPlanningDate, txtTalkDate);
        } else {
            txtTalkDate = (TextView) convertView.getTag(R.id.txtPlanningDate);
        }

        Resources res = context.getResources();
        GroupedTalks gTalk = getGroup(groupPosition);
        DateFormat format = SimpleDateFormat.getDateTimeInstance(
                SimpleDateFormat.MEDIUM, SimpleDateFormat.SHORT);
        txtTalkDate.setText(String.format(
                res.getString(R.string.label_talk_date),
                format.format(gTalk.getDate())));

        return convertView;
    }

    /*
     * (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
     */
    public View getChildView(int groupPosition, int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {

        TextView txtTalkTitre;
        TextView txtTalkSalle;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.planning_row, null);
            txtTalkSalle = (TextView) convertView
                    .findViewById(R.id.txtPlanningSalle);
            convertView.setTag(R.id.txtPlanningSalle, txtTalkSalle);
            txtTalkTitre = (TextView) convertView
                    .findViewById(R.id.txtPlanningTalk);
            convertView.setTag(R.id.txtPlanningTalk, txtTalkTitre);
        } else {
            txtTalkSalle = (TextView) convertView.getTag(R.id.txtPlanningSalle);
            txtTalkTitre = (TextView) convertView.getTag(R.id.txtPlanningTalk);
        }

        Resources res = context.getResources();
        Talk talk = getChild(groupPosition, childPosition);
        txtTalkTitre.setText(String.format(
                res.getString(R.string.label_talk_titre), talk.getFormat(),
                talk.getTitle()));
        txtTalkSalle.setText(String.format(
                res.getString(R.string.label_talk_salle),
                talk.getSalleSession()));

        return convertView;
    }
}
