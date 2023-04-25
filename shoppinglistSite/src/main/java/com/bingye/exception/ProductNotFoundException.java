package com.bingye.exception;

public class ProductNotFoundException extends RuntimeException {
  
	private static final long serialVersionUID = 179856888745840942L;

	public ProductNotFoundException(Long id) {
        super("Product id not found : " + id);
    }
	
	public ProductNotFoundException(String message, Long id) {
        super("Product id not found : " + id + " TODO " + message);
    }
}
