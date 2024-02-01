package com.vikky.app.exception;

public class ResourceNotProcessedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceNotProcessedException() {
		super();
	}

	public ResourceNotProcessedException(String message) {
		super(message);
	}
}
