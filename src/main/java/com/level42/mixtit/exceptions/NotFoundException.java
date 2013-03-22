package com.level42.mixtit.exceptions;

/**
 * Exception spécifique déclenchée lorsqu'un élément n'est
 * pas trouvé par son identifiant lors des appels Webservices
 */
public class NotFoundException extends Exception {

	private static final long serialVersionUID = -393040476228798643L;

	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException(Exception e) {
		super(e.getMessage(), e);
	}
}
