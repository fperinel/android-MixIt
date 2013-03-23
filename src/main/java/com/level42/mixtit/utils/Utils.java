package com.level42.mixtit.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;

import com.level42.mixtit.BuildConfig;
import com.level42.mixtit.exceptions.TechnicalException;


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
	
	/**
	 * Contrôle si le thread courant est le thread principal
	 */
    public static void checkOnMainThread() {
        if (BuildConfig.DEBUG) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                throw new IllegalStateException("This method should be called from the Main Thread");
            }
        }
    }
    
    /**
     * Retourne une image bitmap à partir d'une URL
     * 
     * @param imageUrl URL de l'image
     * @return Bitmap 
     * 
     * @throws TechnicalException
     */
    public static Bitmap loadBitmap(String imageUrl) throws TechnicalException {
    	try {
    		return BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
		} catch (MalformedURLException e) {
			throw new TechnicalException(e.getMessage(), e);
		} catch (IOException e) {
			throw new TechnicalException(e.getMessage(), e);
		}
    }
}
