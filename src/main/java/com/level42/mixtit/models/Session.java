package com.level42.mixtit.models;

import java.util.Date;

/**
 * Classe représentant le planning
 */
public class Session {

	private Integer sessionId;
	
	private String salle;
	
	private String date;
	
	private Date dateFormat;
	
	/**
	 * @return the sessionId
	 */
	public Integer getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the salle
	 */
	public String getSalle() {
		return salle;
	}

	/**
	 * @param salle the salle to set
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
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the dateFormat
	 */
	public Date getDateFormat() {
		dateFormat = new Date(this.date);
		return dateFormat;
	}
}
