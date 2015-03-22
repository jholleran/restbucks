package com.restbucks.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="Conflict found with order")  // 409
public class ConflictException extends RuntimeException {

	public ConflictException(Long id) {
		super("Conflict found with order: " + id);
	}

}
