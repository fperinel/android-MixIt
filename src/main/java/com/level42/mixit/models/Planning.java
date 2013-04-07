package com.level42.mixit.models;

import java.util.List;

/**
 * Classe représentant le planning
 */
public class Planning {

    private List<Session> sessions;

    /**
     * @return the sessions
     */
    public List<Session> getSessions() {
        return sessions;
    }

    /**
     * @param sessions
     *            the sessions to set
     */
    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
