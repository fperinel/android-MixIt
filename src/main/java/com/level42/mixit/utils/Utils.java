package com.level42.mixit.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;

import com.level42.mixit.BuildConfig;
import com.level42.mixit.exceptions.TechnicalException;

/**
 * Classe utilitaire.
 */
public class Utils {

    /**
     * Tag des logs.
     */
    public final static String logTag = "[MIXIT]";

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
    public static Bitmap loadBitmap(String imageUrl) throws TechnicalException {
        try {
            return BitmapFactory.decodeStream((InputStream) new URL(imageUrl)
                    .getContent());
        } catch (MalformedURLException e) {
            throw new TechnicalException(e.getMessage(), e);
        } catch (IOException e) {
            throw new TechnicalException(e.getMessage(), e);
        }
    }
}
