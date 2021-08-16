package com.ideaportal.exception;


public class ThemeNameSameException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	private final String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}


	public ThemeNameSameException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

}