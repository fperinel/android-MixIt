package com.level42.mixtit.activities;

import roboguice.activity.RoboTabActivity;
import roboguice.inject.ContentView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.google.inject.Inject;
import com.level42.mixtit.R;
import com.level42.mixtit.models.Session;
import com.level42.mixtit.services.IPlanningService;


/**
 * Ecran d'activité
 */
@ContentView(R.layout.activity_activite)
public class ActiviteActivity extends RoboTabActivity {
	
	@Inject
	private IPlanningService service;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Session session = service.getPlanningSession(127);
		
		TabHost tabHost = getTabHost();
		
		Intent talkList = new Intent(this, TalkListActivity.class);
		TabSpec tabSpec1 = tabHost.newTabSpec("tab_talks").setIndicator(
					this.getText(R.string.tab_talks), 
					getResources().getDrawable(R.drawable.ic_launcher)
				).setContent(talkList);
		tabHost.addTab(tabSpec1);
		
		Intent lTalkList = new Intent(this, LightningTalkListActivity.class);
		TabSpec tabSpec2 = tabHost.newTabSpec("tab_ltalks").setIndicator(
					this.getText(R.string.tab_ltalks), 
					getResources().getDrawable(R.drawable.ic_launcher)
				).setContent(lTalkList);
		tabHost.addTab(tabSpec2);
		
	
		/*tabSpec = tabHost.newTabSpec("tab_staff").setIndicator(
					this.getText(R.string.tab_staff), 
					getResources().getDrawable(R.drawable.ic_launcher)
				).setContent(intent);
		tabHost.addTab(tabSpec);
		
		tabSpec = tabHost.newTabSpec("tab_speakers").setIndicator(
					this.getText(R.string.tab_speakers), 
					getResources().getDrawable(R.drawable.ic_launcher)
				).setContent(intent);
		tabHost.addTab(tabSpec);
		
		tabSpec = tabHost.newTabSpec("tab_sponsors").setIndicator(
					this.getText(R.string.tab_sponsors), 
					getResources().getDrawable(R.drawable.ic_launcher)
				).setContent(intent);
		tabHost.addTab(tabSpec);*/
	}

}