package com.bingye.service.impl;

import java.util.List;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bingye.exception.ProductNotFoundException;
import com.bingye.model.Product;
import com.bingye.repository.ProductRepository;
import com.bingye.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
    @Autowired 
    private ProductRepository productRepository;

   	@Override
	public List<Product> getAllProducts(String username) {
   		logger.trace("Entered getAllProducts method");
   		
   		List<Product> products = productRepository.findAll();

		return products;
	}

	@Override
	public Product getProduct(String username, long id) {
		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
	}

	@Override
	public void deleteProduct(String username, long id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			productRepository.deleteById(id);
		} else {
			throw new ProductNotFoundException(id);
		}
		
	}

	@Override
	public Product updateProduct(String username, long id, Product product) {
		Product productUpdated = productRepository.save(product);
		return productUpdated;
	}

	@Override
	public Product createProduct(String username, Product product) {
		Product createdproduct = productRepository.save(product);
		return createdproduct;
		
	}
    
}
