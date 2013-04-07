package com.level42.mixit.exceptions;

/**
 * Exception spécifique déclenchée lors d'une exception de type technique
 */
public class TechnicalException extends Exception {

    /**
     * Serial de l'objet
     */
    private static final long serialVersionUID = -393040476228798643L;

    /**
     * Constructeur
     * @param message Message d'erreur technique
     * @param e Exception parente
     */
    public TechnicalException(String message, Exception e) {
	super(message, e);
    }
}
