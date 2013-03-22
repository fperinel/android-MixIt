package com.level42.mixtit;

import android.app.Application;
import android.content.Context;

/**
 * Application
 */
public class MixItApplication extends Application {

	private static Context context;
	
	@Override
	protected void attachBaseContext(Context base) {
		context = base;
	}
	
	public static Context getAppContext() {
		return MixItApplication.context;
	}
}
