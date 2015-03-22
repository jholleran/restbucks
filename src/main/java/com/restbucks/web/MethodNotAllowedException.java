package com.restbucks.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED, reason="Method not allowed")  // 405
public class MethodNotAllowedException extends RuntimeException {

	public MethodNotAllowedException(Long id) {
		super("Method not allowed on order: " + id);
	}

}
