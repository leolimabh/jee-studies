package com.leandro.app.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public @Data class FieldNotValidException extends RuntimeException {
	private static final long serialVersionUID = 2527136873207124029L;
	private final String fieldName;

	public FieldNotValidException(final String fieldName, final String message) {
		super(message);
		this.fieldName = fieldName;
	}

	@Override
	public String toString() {
		return "FieldNotValidException [fieldName =" + fieldName + "]";
	}

}
