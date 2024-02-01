package com.vikky.app.exception;

public class RefreshTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RefreshTokenException() {
		super();
	}

	public RefreshTokenException(String message) {
		super(message);
	}
}
