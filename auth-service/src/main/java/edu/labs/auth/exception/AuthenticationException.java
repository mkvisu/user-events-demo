package edu.labs.auth.exception;

public class AuthenticationException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public AuthenticationException(String msg, String userName) {
		super(String.format("Error %s authenticating user %s", msg, userName));
	}
}
