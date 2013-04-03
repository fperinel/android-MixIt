package com.level42.mixit.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * Classe représentant un tag
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Interest {

	private Integer id;

	private String name;

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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
