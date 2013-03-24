package com.level42.mixit.exceptions;

/**
 * Exception spécifique déclenchée lorsqu'une source de données locale
 * n'est pas accessible
 */
public class DataAccessException extends Exception {

	private static final long serialVersionUID = 2143427646788162775L;

	public DataAccessException(Exception e) {
		super(e.getMessage(), e);
	}
}
