package com.level42.mixit.exceptions;

/**
 * Exception spécifique déclenchée lorsqu'une source de données locale n'est pas accessible.
 */
public class DataAccessException extends Exception {

    /**
     * Serial de l'objet.
     */
    private static final long serialVersionUID = 2143427646788162775L;

    /**
     * Constructeur.
     * @param e Exception parente
     */
    public DataAccessException(final Exception e) {
        super(e.getMessage(), e);
    }
}
