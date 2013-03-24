package com.level42.mixit.exceptions;

/**
 * Exception spécifique déclenchée lors d'une exception 
 * de type technique
 */
public class TechnicalException extends Exception {

	private static final long serialVersionUID = -393040476228798643L;

	public TechnicalException(String message, Exception e) {
		super(message, e);
	}
}
