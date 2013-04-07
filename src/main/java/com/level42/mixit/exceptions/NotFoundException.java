package com.level42.mixit.exceptions;

/**
 * Exception spécifique déclenchée lorsqu'un élément n'est pas trouvé par son
 * identifiant lors des appels Webservices
 */
public class NotFoundException extends Exception {

    /**
     * Serial de l'objet
     */
    private static final long serialVersionUID = -393040476228798643L;

    /**
     * Constructeur
     * 
     * @param message
     *            Message d'erreur
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Constructeur
     * 
     * @param e
     *            Exception parente
     */
    public NotFoundException(Exception e) {
        super(e.getMessage(), e);
    }
}
