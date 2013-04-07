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

public class PlanningAdapter extends BaseExpandableListAdapter {

    /**
     * Liste des talks
     */
    private List<GroupedTalks> talks = Collections.emptyList();

    private Context context;

    /**
     * Constructeur de l'adaptateur
     * 
     * @param lives
     */
    public PlanningAdapter(Context context) {
        this.context = context;
    }

    public void updateTalks(List<GroupedTalks> talks) {
        Utils.checkOnMainThread();
        this.talks = talks;
        notifyDataSetChanged();
    }

    public Talk getChild(int groupPosition, int childPosition) {
        return talks.get(groupPosition).getTalks().get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return talks.get(groupPosition).getTalks().get(childPosition).getId();
    }

    public int getChildrenCount(int groupPosition) {
        return talks.get(groupPosition).getTalks().size();
    }

    public GroupedTalks getGroup(int groupPosition) {
        return talks.get(groupPosition);
    }

    public int getGroupCount() {
        return talks.size();
    }

    public long getGroupId(int groupPosition) {
        return talks.get(groupPosition).getId();
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

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
