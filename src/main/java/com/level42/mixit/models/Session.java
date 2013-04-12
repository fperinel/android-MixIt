package com.level42.mixit.models;


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
}
