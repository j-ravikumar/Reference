package com.journaldev.spring.controller;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3576765337421507930L;

	public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
