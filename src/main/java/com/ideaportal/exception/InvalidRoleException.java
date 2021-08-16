package com.ideaportal.exception;

public class InvalidRoleException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String errorMessage;
	
	public InvalidRoleException(String errorMessage) {
		
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
