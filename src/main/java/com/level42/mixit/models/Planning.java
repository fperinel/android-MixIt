package com.level42.mixit.models;

import java.util.List;

/**
 * Classe représentant le planning.
 */
public class Planning {

    /**
     * Liste des sessions.
     */
    private List<Session> sessions;

    /**
     * Retourne la liste des sessions.
     * @return the sessions
     */
    public List<Session> getSessions() {
        return sessions;
    }

    /**
     * Renseigne la liste des sessions.
     * @param sessions
     *            the sessions to set
     */
    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
