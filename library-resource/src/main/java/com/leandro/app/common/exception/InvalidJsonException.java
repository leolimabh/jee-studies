package com.leandro.app.common.exception;

public class InvalidJsonException extends RuntimeException {

	private static final long serialVersionUID = -2462636377130546301L;

	public InvalidJsonException(final String message) {
		super(message);
	}

	public InvalidJsonException(final Throwable throwable) {
		super(throwable);
	}
}
