package com.qeema.authorizationserver.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GenericController {
	protected Logger logger =  LoggerFactory.getLogger(GenericController.class);

	@ExceptionHandler(Exception.class)
	public HttpEntity<?> handleException(Exception e) {
		logger.error(e.getMessage());
		return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

//	@ExceptionHandler(RuntimeException.class)
//	public HttpEntity<?> handleRuntimeException(RuntimeException e) {
//		return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	public HttpEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		logger.error(e.getMessage());
		return new ResponseEntity<>("Duplicate data, please check uniqueness", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IOException.class)
	public HttpEntity<?> handleIOException(IOException e) {
		logger.error(e.getMessage());
		return new ResponseEntity<>("not valid Token", HttpStatus.BAD_REQUEST);
	}
	
//	@ExceptionHandler(SqlExceptionHelper.class)
//	public HttpEntity<?> handleSqlExceptionHelpern(SqlExceptionHelper e) {
//		return new ResponseEntity<>(e.toString(), HttpStatus.ACCEPTED);
//	}
}
