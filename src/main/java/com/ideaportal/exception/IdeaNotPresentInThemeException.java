package com.ideaportal.exception;

public class IdeaNotPresentInThemeException extends Exception{
    private static final long serialVersionUID = 1L;

    private final String errorMessage;

    public IdeaNotPresentInThemeException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}