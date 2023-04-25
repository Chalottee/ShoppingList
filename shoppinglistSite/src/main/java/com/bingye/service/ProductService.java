package com.bingye.service;

import java.util.List;

import com.bingye.model.Product;

public interface ProductService {

	List<Product> getAllProducts(String username);
	
	Product getProduct(String username, long id);
	
	void deleteProduct(String username, long id);
	
	Product updateProduct(String username, long id, Product product);
	
	Product createProduct(String username, Product product);
}
