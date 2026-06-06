package com.invoice.exception;

import org.springframework.dao.DataAccessException;

public class DBAccessException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private DataAccessException exception;
	
	public DBAccessException() {
		
	}
	
	public DBAccessException(DataAccessException e) {
		this.exception = e;
	}

	public DataAccessException getException() {
		return exception;
	}

	public void setException(DataAccessException exception) {
		this.exception = exception;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
