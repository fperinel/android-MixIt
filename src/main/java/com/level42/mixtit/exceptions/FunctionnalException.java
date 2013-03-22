package com.level42.mixtit.exceptions;

/**
 * Exception spécifique déclenchée lors d'une exception 
 * de type fonctionnelle
 */
public class FunctionnalException extends Exception {

	private static final long serialVersionUID = -393040476228798643L;

	public FunctionnalException(String message, Exception e) {
		super(message, e);
	}
}
