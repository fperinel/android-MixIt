package com.level42.mixit.activities;

import roboguice.activity.RoboTabActivity;
import roboguice.inject.ContentView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.level42.mixit.R;

/**
 * Ecran principal affichant les onglets de navigation.
 */
@ContentView(R.layout.activity_activite)
public class ActiviteActivity extends RoboTabActivity {

    /*
     * (non-Javadoc)
     * @see roboguice.activity.RoboTabActivity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TabHost tabHost = getTabHost();

        Intent planning = new Intent(this, PlanningActivity.class);
        TabSpec tabSpec0 = tabHost
                .newTabSpec("tab_planning")
                .setIndicator(this.getText(R.string.tab_planning),
                        getResources().getDrawable(R.drawable.ic_launcher))
                .setContent(planning);
        tabHost.addTab(tabSpec0);

        Intent talkList = new Intent(this, TalkListActivity.class);
        TabSpec tabSpec1 = tabHost
                .newTabSpec("tab_talks")
                .setIndicator(this.getText(R.string.tab_talks),
                        getResources().getDrawable(R.drawable.ic_launcher))
                .setContent(talkList);
        tabHost.addTab(tabSpec1);

        Intent lTalkList = new Intent(this, LightningTalkListActivity.class);
        TabSpec tabSpec2 = tabHost
                .newTabSpec("tab_ltalks")
                .setIndicator(this.getText(R.string.tab_ltalks),
                        getResources().getDrawable(R.drawable.ic_launcher))
                .setContent(lTalkList);
        tabHost.addTab(tabSpec2);
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_adresse:
            Intent adresseActivity = new Intent(ActiviteActivity.this,
                    AdresseActivity.class);
            ActiviteActivity.this.startActivity(adresseActivity);
            break;
        case R.id.menu_billet:
            Toast.makeText(getApplicationContext(), "Menu mon billet",
                    Toast.LENGTH_LONG).show();
            break;
        default:
            break;
        }
        return true;
    }

}