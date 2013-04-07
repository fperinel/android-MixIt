package com.level42.mixit.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Classe représentant un lien
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {

    private Integer id;

    private String name;

    private String url;

    private Integer ordernum;

    /**
     * @return the id
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
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the url
     */
    public String getUrl() {
	return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
	this.url = url;
    }

    /**
     * @return the ordernum
     */
    public Integer getOrdernum() {
	return ordernum;
    }

    /**
     * @param ordernum
     *            the ordernum to set
     */
    public void setOrdernum(Integer ordernum) {
	this.ordernum = ordernum;
    }
}
