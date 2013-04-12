package com.level42.mixit.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.util.Log;

import com.level42.mixit.BuildConfig;
import com.level42.mixit.exceptions.TechnicalException;

/**
 * Classe utilitaire.
 */
public final class Utils {

    /**
     * Tag des logs.
     */
    public static final String LOGTAG = "[MIXIT]";

    /**
     * On masque le constructeur par défaut.
     */
    private Utils() {
    }
    
    /**
     * Contrôle si le thread courant est le thread principal.
     */
    public static void checkOnMainThread() {
        if (BuildConfig.DEBUG && Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new IllegalStateException("This method should be called from the Main Thread");
        }
    }

    /**
     * Retourne une image bitmap à partir d'une URL.
     * @param imageUrl URL de l'image
     * @return Bitmap
     * @throws TechnicalException
     */
    public static Bitmap loadBitmap(final String imageUrl) throws TechnicalException {
        try {
            return BitmapFactory.decodeStream((InputStream) new URL(imageUrl)
                    .getContent());
        } catch (MalformedURLException e) {
            throw new TechnicalException(e.getMessage(), e);
        } catch (IOException e) {
            throw new TechnicalException(e.getMessage(), e);
        }
    }
    
    /**
     * Transforme des chaines de caractère dates : 2013-04-25T09:30:00.000+02:00
     * en objet date
     * @param dateStr chaines de caractère date
     * @return Objet date
     */
    public static Date formatWsDate(final String dateStr) {
        try {
            //2013-04-25T09:30:00.000+02:00
            SimpleDateFormat formatOrigin = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            return formatOrigin.parse(dateStr);
        } catch (ParseException e) {
            Log.w(Utils.LOGTAG, "Date format exception");
            return null;
        }
    }
}
