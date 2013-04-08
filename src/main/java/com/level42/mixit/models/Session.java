package com.level42.mixit.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.level42.mixit.utils.Utils;

import android.util.Log;

/**
 * Classe représentant une session de talk.
 */
public class Session {

    /**
     * Identifiant de la session.
     */
    private Integer id;

    /**
     * Salle de la session.
     */
    private String salle;

    /**
     * Date de la session.
     */
    private String date;

    /**
     * Date formatée de la session.
     */
    private Date dateFormat;

    /**
     * Retourne l'identiant.
     * @return the sessionId
     */
    public Integer getId() {
        return id;
    }

    /**
     * Renseigne l'identifiant.
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retourne la salle de la session.
     * @return the salle
     */
    public String getSalle() {
        return salle;
    }

    /**
     * Renseigne la salle de la session.
     * @param salle
     *            the salle to set
     */
    public void setSalle(String salle) {
        this.salle = salle;
    }

    /**
     * Retourne la date de la session.
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Renseigne la date de la session.
     * @param date
     *            the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Retourne la date formatée de la session.
     * @return the dateFormat
     */
    public Date getDateFormat() {
        if (dateFormat == null) {
            try {
                SimpleDateFormat formatOrigin = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                dateFormat = formatOrigin.parse(this.date);
            } catch (ParseException e) {
                Log.w(Utils.LOGTAG, "Date format exception");
                return null;
            }
        }
        return dateFormat;
    }
}
