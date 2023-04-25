package com.bingye.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bingye.BaseIntegrationTest;
import com.bingye.SprintBootCrudApplication;
import com.bingye.exception.ProductNotFoundException;
import com.bingye.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SprintBootCrudApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = OrderAnnotation.class)
@ActiveProfiles("test")
public class ProductControllerIntegrationTest extends BaseIntegrationTest {
    @LocalServerPort
    private int port;

	@Autowired
	private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void addProduct() {

    	Product product = new Product(10001, "bingye", "book", "increase knowledge","20");

        HttpEntity<Product> entity = new HttpEntity<>(product, getHttpHeader());

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/accounts/bingye/products"),
                HttpMethod.POST, entity, String.class);

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        //compare http status code and json data
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        assertTrue(actual.contains("/accounts/bingye/products"));

    }
    
    @Test
    @Order(2)
    public void updateProduct() throws JSONException {

    	Product product = new Product(1,  "bingye", "book", "increase knowledge updated","20");

        HttpEntity<Product> entity = new HttpEntity<>(product, getHttpHeader());

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/accounts/bingye/products/1"),
                HttpMethod.PUT, entity, String.class);
        
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());

        String expected = "{\"id\":1,\"username\":\"bingye\",\"title\":\"book\",\"description\":\"increase knowledge updated\",\"price\":\"20\"}";

        JSONAssert.assertEquals(expected, response.getBody(), false);

    }
    
    @Test
    @Order(3)
    public void testGetProduct() throws JSONException, JsonProcessingException {

        HttpEntity<String> entity = new HttpEntity<>(null, getHttpHeader());

        ResponseEntity<String> response1 = restTemplate.exchange(
                createURLWithPort("/accounts/bingye/products/1"),
                HttpMethod.GET, entity, String.class);

        String expected = "{\"id\":1,\"username\":\"bingye\",\"title\":\"book\",\"description\":\"increase knowledge updated\",\"price\":\"20\"}";

        JSONAssert.assertEquals(expected, response1.getBody(), false);
        
    }
    
	@Test
	@Order(4)
	public void testDeleteProduct() {
		Product product = restTemplate.getForObject(createURLWithPort("/accounts/bingye/products/1"), Product.class);
		assertNotNull(product);

		HttpEntity<String> entity = new HttpEntity<>(null, getHttpHeader());

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/accounts/bingye/products/1"),
                HttpMethod.DELETE, entity, String.class);
		
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());

		try {
			product = restTemplate.getForObject("/accounts/bingye/products/1", Product.class);
		} catch (ProductNotFoundException e) {
			assertEquals("Product id not found : 1", e.getMessage());
		}
	}

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
