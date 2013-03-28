package com.level42.mixit.activities;

import roboguice.activity.RoboTabActivity;
import roboguice.inject.ContentView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch(item.getItemId())
	    {
		    case R.id.menu_adresse:
		    	Toast.makeText(getApplicationContext(), "Menu adresse", Toast.LENGTH_LONG).show();
		    	break;
		    case R.id.menu_billet:
		    	Toast.makeText(getApplicationContext(), "Menu mon billet", Toast.LENGTH_LONG).show();
		        break;
		    default:
		    	break;
	    }
	    return true;
	}

}