package com.level42.mixit.exceptions;

/**
 * Exception spécifique aux erreurs de communication dans les appels Webservices.
 */
public class CommunicationException extends Exception {

    /**
     * Serial de l'objet.
     */
    private static final long serialVersionUID = -142143821715631857L;

    /**
     * Constructeur.
     * @param eException parente
     */
    public CommunicationException(final Exception e) {
        super(e.getMessage(), e);
    }
}
