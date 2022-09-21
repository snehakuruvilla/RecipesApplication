package com.sk.recipe.exception;

public class MapperNullObjectException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = -113972826874995794L;

	public MapperNullObjectException() {
    }

    public MapperNullObjectException(String message) {
        super(message);
    }

    public MapperNullObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperNullObjectException(Throwable cause) {
        super(cause);
    }

    public MapperNullObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
