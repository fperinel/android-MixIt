package com.level42.mixtit.activities;

import roboguice.activity.RoboSplashActivity;
import roboguice.inject.ContentView;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;

import com.level42.mixtit.R;

/**
 * Activité principale de l'application
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends RoboSplashActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);	
	}

	@Override
	protected void startNextActivity() {
		//Intent intent = new Intent(this.getApplicationContext(), TalkListActivity.class);
		Intent intent = new Intent(this.getApplicationContext(), ActiviteActivity.class);
		this.startActivity(intent);
	}
	
	@Override
	protected void doStuffInBackground(Application app) {
		Looper.prepare();
		super.doStuffInBackground(app);
    }
}

