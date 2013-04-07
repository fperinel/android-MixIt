package com.level42.mixit.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe représentant le planning
 */
public class Session {

    private Integer id;

    private String salle;

    private String date;

    private Date dateFormat;

    /**
     * @return the sessionId
     */
    public Integer getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
	this.id = id;
    }

    /**
     * @return the salle
     */
    public String getSalle() {
	return salle;
    }

    /**
     * @param salle
     *            the salle to set
     */
    public void setSalle(String salle) {
	this.salle = salle;
    }

    /**
     * @return the date
     */
    public String getDate() {
	return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(String date) {
	this.date = date;
    }

    /**
     * @return the dateFormat
     */
    public Date getDateFormat() {
	if (dateFormat == null) {
	    try {
		SimpleDateFormat formatOrigin = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
		dateFormat = formatOrigin.parse(this.date);
	    } catch (ParseException e) {
		e.printStackTrace();
		return null;
	    }
	}
	return dateFormat;
    }
}
