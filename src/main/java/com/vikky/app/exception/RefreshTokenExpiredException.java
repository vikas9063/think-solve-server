package com.vikky.app.exception;

public class RefreshTokenExpiredException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public RefreshTokenExpiredException() {
		super();
	}

	public RefreshTokenExpiredException(String message) {
		super(message);
	}
}
