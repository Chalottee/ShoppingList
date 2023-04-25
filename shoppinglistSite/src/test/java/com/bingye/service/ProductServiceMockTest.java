package com.bingye.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bingye.exception.ProductNotFoundException;
import com.bingye.model.Product;
import com.bingye.repository.ProductRepository;
import com.bingye.service.impl.ProductServiceImpl;

@ExtendWith(SpringExtension.class)
public class ProductServiceMockTest {
	
	@Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService = new ProductServiceImpl();

    @Test
    public void getAllProducts() {
    	List<Product> products = Arrays.asList(
                new Product(10001, "bingye", "book","increase knowledge","20"),
                new Product(10002, "bingye", "pen","write knowledge","2"));
    	
    	//compare results of service and repository
    	when(productRepository.findAll()).thenReturn(products);
		assertEquals(products, productService.getAllProducts("bingye"));
    }
    
    @Test
    public void getProduct() {
    	Product product = new Product(10001, "bingye", "book", "increase knowledge","20");
    	
    	when(productRepository.findById(Long.valueOf(10001))).thenReturn(Optional.of(product));
		assertEquals(product, productService.getProduct("bingye", Long.valueOf(10001)));
    }
    
    @Test
    public void getProductNotFound() {
    	
    	ProductNotFoundException exception = assertThrows(
    			ProductNotFoundException.class,
    	           () -> productService.getProduct("bingye", Long.valueOf(10001)),
    	           "Product id not found : 10001"
    	    );

    	    assertEquals("Product id not found : 10001", exception.getMessage());
    }
    
    @Test
    public void deleteProduct() {
    	
    	Product product = new Product(10001, "bingye","book", "increase knowledge","20");
    	
    	when(productRepository.findById(Long.valueOf(10001))).thenReturn(Optional.of(product));
    	productService.deleteProduct("bingye", Long.valueOf(10001));

		verify(productRepository, times(1)).deleteById(Long.valueOf(10001));
    }
    
    @Test
    public void updateProduct() {
    	Product product = new Product(10001, "bingye2","book", "increase knowledge","20");
    	
    	when(productRepository.save(product)).thenReturn(product);
		assertEquals(product, productService.updateProduct("bingye", Long.valueOf(10001), product));
    }
    
    @Test
    public void createProduct() {
    	Product product = new Product(10001, "bingye","book", "increase knowledge","20");
    	
    	when(productRepository.save(product)).thenReturn(product);
		assertEquals(product, productService.createProduct("bingye", product));

    }

}
