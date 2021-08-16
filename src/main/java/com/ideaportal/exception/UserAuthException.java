package com.ideaportal.exception;

public class UserAuthException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public UserAuthException(String errorMessage) {
        super(errorMessage);
        this.errorMessage=errorMessage;
    }
}
