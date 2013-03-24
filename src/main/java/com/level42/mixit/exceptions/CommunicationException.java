package com.level42.mixit.exceptions;

/**
 * Exception spécifique aux erreurs de communication dans les appels Webservices
 */
public class CommunicationException extends Exception {

	private static final long serialVersionUID = -142143821715631857L;

	public CommunicationException(Exception e) {
		super(e.getMessage(), e);
	}
}
