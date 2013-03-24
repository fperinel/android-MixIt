package com.level42.mixit.services.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.level42.mixit.MixItApplication;
import com.level42.mixit.R;
import com.level42.mixit.models.Talk;
import com.level42.mixit.utils.Utils;

public class PlanningAdapter extends BaseAdapter {

	/**
	 * Liste des talks
	 */
	private List<Talk> talks = Collections.emptyList();

	private Context context;
	
	/**
	 * Constructeur de l'adaptateur
	 * 
	 * @param lives
	 */
	public PlanningAdapter(Context context) {
		this.context = context;
	}

	public int getCount() {
		return (talks != null ? talks.size() : 0);
	}

	public Talk getItem(int arg0) {
		return talks.get(arg0);
	}

	public long getItemId(int arg0) {
		Talk talk= (Talk) this.getItem(arg0);
		if (talk != null) {
			return talk.getId();
		}
		return -1;
	}

    public void updateTalks(List<Talk> talks) {
    	Utils.checkOnMainThread();
        this.talks = talks;
        notifyDataSetChanged();
    }
	
	public View getView(int position, View convertView, ViewGroup parent) {

	    Talk talk = getItem(position);
	    
		if (talk.getDateSession() == null) {
			Log.w(Utils.LOGTAG, "No session date for talk " + talk.getTitle() + "(id:" + talk.getId().toString() + ")");
			return null;
		}
		
	    TextView txtPlanningDate;
	    TextView txtPlanningSalle;
		TextView txtPlanningTalk;
	    
	    if (convertView == null) {
	        convertView = View.inflate(context, R.layout.planning_row, null);
	        
	        txtPlanningTalk = (TextView) convertView.findViewById(R.id.txtPlanningTalk);
	        txtPlanningDate = (TextView) convertView.findViewById(R.id.txtPlanningDate);
	        txtPlanningSalle = (TextView) convertView.findViewById(R.id.txtPlanningSalle);
	        
	        convertView.setTag(R.id.txtPlanningTalk, txtPlanningTalk);
	        convertView.setTag(R.id.txtPlanningDate, txtPlanningDate);
	        convertView.setTag(R.id.txtPlanningSalle, txtPlanningSalle);
	        
	    } else {
	    	txtPlanningTalk = (TextView) convertView.getTag(R.id.txtPlanningTalk);
	    	txtPlanningDate = (TextView) convertView.getTag(R.id.txtPlanningDate);
	    	txtPlanningSalle = (TextView) convertView.getTag(R.id.txtPlanningSalle);
	    }

		Resources res = this.context.getResources();
		
	    
	    txtPlanningTalk.setText(talk.getTitle());
	    
	    txtPlanningSalle.setText(String.format(res.getString(R.string.label_talk_salle), talk.getSalleSession()));
	    
		DateFormat format = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MEDIUM, SimpleDateFormat.SHORT);
	    txtPlanningDate.setText(String.format(res.getString(R.string.label_talk_date), format.format(talk.getDateSession())));
	    
	    return convertView;
	}

}
