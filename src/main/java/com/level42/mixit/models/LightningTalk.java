package com.level42.mixit.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Classe représentant un LightningTalk
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LightningTalk extends Talk {

    private Integer nbVotes;

    /**
     * @return the nbVotes
     */
    public Integer getNbVotes() {
        return nbVotes;
    }

    /**
     * @param nbVotes
     *            the nbVotes to set
     */
    public void setNbVotes(Integer nbVotes) {
        this.nbVotes = nbVotes;
    }
}
