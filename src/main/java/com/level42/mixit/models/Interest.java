package com.level42.mixit.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Classe représentant un tag "centre d'intérêt"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Interest {

    /**
     * Identifiant du tag
     */
    private Integer id;

    /**
     * Nom du tag
     */
    private String name;

    /**
     * Retourne l'identifiant du tag
     * 
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Renseigne l'identifiant du tag
     * 
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retourne le nom du centre d'intérêt
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Renseigne le nom du centre d'intérêt
     * 
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
