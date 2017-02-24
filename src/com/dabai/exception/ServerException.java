package com.dabai.exception;

public class ServerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 192119505115409053L;

	public ServerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServerException(String message) {
		super(message);
	}
	
}
