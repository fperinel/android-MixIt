package com.level42.mixit.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Classe représentant un lien
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {

    /**
     * Identifiant du lien
     */
    private Integer id;

    /**
     * Nom du lien
     */
    private String name;

    /**
     * Url du lien
     */
    private String url;

    /**
     * Ordre du lien
     */
    private Integer ordernum;

    /**
     * Retourne l'identifiant du lien
     * 
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Renseigne l'identifiant du lien
     * 
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retourne le nom du lein
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Renseigne le nom du lien
     * 
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne l'URL du lien
     * 
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Renseigne l'URL du lien
     * 
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Retourne l'ordre du lien
     * 
     * @return the ordernum
     */
    public Integer getOrdernum() {
        return ordernum;
    }

    /**
     * Renseigne l'ordre du lien
     * 
     * @param ordernum
     *            the ordernum to set
     */
    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }
}
