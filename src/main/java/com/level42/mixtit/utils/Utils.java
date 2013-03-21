package com.level42.mixtit.utils;

import android.os.Looper;

import com.level42.mixtit.BuildConfig;


/**
 * Classe utilitaire
 */
public class Utils {

	public static String LOGTAG = "[MIXIT]";
	
	/**
	 * Indique si un réseau data est disponible
	 * 
	 * @return Vrai si un réseau data est disponible
	 */
	public static boolean isNetworkAvailable() {
		/*ConnectivityManager connectivityManager = (ConnectivityManager)Application.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null;*/
		
		return true;
	}
	
    public static void checkOnMainThread() {
        if (BuildConfig.DEBUG) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                throw new IllegalStateException("This method should be called from the Main Thread");
            }
        }
    }
}
