package com.level42.mixit.activities;

import roboguice.activity.RoboTabActivity;
import roboguice.inject.ContentView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.level42.mixit.R;

/**
 * Ecran d'activité
 */
@ContentView(R.layout.activity_activite)
public class ActiviteActivity extends RoboTabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TabHost tabHost = getTabHost();
		
		Intent planning = new Intent(this, PlanningActivity.class);
		TabSpec tabSpec0 = tabHost.newTabSpec("tab_planning").setIndicator(
					this.getText(R.string.tab_planning), 
					getResources().getDrawable(R.drawable.ic_launcher)
				).setContent(planning);
		tabHost.addTab(tabSpec0);
		
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