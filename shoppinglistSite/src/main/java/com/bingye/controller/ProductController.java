package com.bingye.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bingye.model.Product;
import com.bingye.service.ProductService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController //controller, responsebody
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping("/accounts/{username}/products")
	public List<Product> getAllProducts(@PathVariable String username) {
		
		List<Product> products = productService.getAllProducts(username);
		
		return products;
	}

	@GetMapping("/accounts/{username}/products/{id}")
	public Product getProduct(@PathVariable String username, @PathVariable long id) {
		
		Product product = productService.getProduct(username, id);
		return product;
	}

	@DeleteMapping("/accounts/{username}/products/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable String username, @PathVariable long id) {

		productService.deleteProduct(username, id);

		ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();
		return responseEntity;
	}

	@PutMapping("/accounts/{username}/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable String username, @PathVariable long id,
			@RequestBody Product product) {

		product.setUsername(username);
		
		Product productUpdated = productService.updateProduct(username, id, product);
		
		ResponseEntity<Product> responseEntity = new ResponseEntity<Product>(productUpdated, HttpStatus.OK);

		return responseEntity;
	}

	@PostMapping("/accounts/{username}/products")
	public ResponseEntity<Void> createProduct(@PathVariable String username, @RequestBody Product product) {
		
		product.setUsername(username);

		
		Product createdProduct = productService.createProduct(username, product);
		
		if (createdProduct == null)
			return ResponseEntity.noContent().build();

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdProduct.getId())
				.toUri(); 

		return ResponseEntity.created(uri).build();
	}

}