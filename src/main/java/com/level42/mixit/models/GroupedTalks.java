package com.level42.mixit.models;

import java.util.Date;
import java.util.List;

/**
 * Classe représentant le planning avec les talks regroupés par date.
 */
public class GroupedTalks {

    /**
     * Identifiant unique.
     */
    private Integer id;

    /**
     * Date de la session du talk.
     */
    private Date date;

    /**
     * Liste des talks
     */
    private List<Talk> talks;

    /**
     * Renseigne la date de la session.
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Retourne la date de la session.
     * @param date
     *            the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Retourne la liste des talks.
     * @return the talks
     */
    public List<Talk> getTalks() {
        return talks;
    }

    /**
     * Renseigne la liste des talks.
     * @param talks
     *            the talks to set
     */
    public void setTalks(List<Talk> talks) {
        this.talks = talks;
    }

    /**
     * Retourne l'identifiant unique.
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Renseigne l'identifiant unique.
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

}