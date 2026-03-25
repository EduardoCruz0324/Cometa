package com.product.exception;

import org.springframework.dao.DataAccessException;

public class DBAccessException extends RuntimeException{
	private static final long serialVersionUID = 1L;

private DataAccessException exception;
	
	public DBAccessException(DataAccessException e) {
		this.exception = e;
	}


}
