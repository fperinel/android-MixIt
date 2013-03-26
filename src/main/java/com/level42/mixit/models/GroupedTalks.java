package com.level42.mixit.models;

import java.util.Date;
import java.util.List;

/**
 * Classe représentant le planning avec les talks regroupés par date
 */
public class GroupedTalks {

	private Integer id;
	
	private Date date;
	
	private List<Talk> talks;

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the talks
	 */
	public List<Talk> getTalks() {
		return talks;
	}

	/**
	 * @param talks the talks to set
	 */
	public void setTalks(List<Talk> talks) {
		this.talks = talks;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}