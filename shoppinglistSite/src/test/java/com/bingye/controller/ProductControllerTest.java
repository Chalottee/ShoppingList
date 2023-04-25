package com.bingye.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bingye.model.Product;
import com.bingye.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    
    private static final ObjectMapper om = new ObjectMapper();

    //TODO move to base class as sample data
    Product mockProduct = new Product(10001, "bingye", "book", "increase knowledge","20");

    String exampleProductJson = "{\"id\":10001,\"username\":\"bingye\",\"title\":\"book\",\"description\":\"increase knowledge\",\"price\":\"20\"}";
    String exampleProductJsonUpdated = "{\"id\":10001,\"username\":\"bingye\",\"title\":\"book\",\"description\":\"increase knowledge updated\",\"price\":\"20\"}";

    @Test
    public void getProduct() throws Exception {

        Mockito.when(productService.getProduct("bingye",10001)).thenReturn(mockProduct);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/accounts/bingye/products/10001").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //compare http status and json code
        JSONAssert.assertEquals(exampleProductJson, result.getResponse().getContentAsString(), false);

    }

    @Test
    public void createProduct() throws Exception {

    	Product product = new Product(10001, "bingye", "book", "increase knowledge","20");

        Mockito.when(productService.createProduct(Mockito.anyString(), Mockito.any(Product.class))).thenReturn(product);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/accounts/bingye/products").content(exampleProductJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/accounts/bingye/products/10001",
                response.getHeader(HttpHeaders.LOCATION));

    }
    
    @Test
    public void updateProduct() throws Exception {

    	Product product = new Product(10001, "bingye", "book", "increase knowledge updated","20");

        Mockito.when(productService.updateProduct(Mockito.anyString(), Mockito.anyLong(), Mockito.any(Product.class))).thenReturn(product);
        
        String productString = om.writeValueAsString(product);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/accounts/bingye/products/10001")
                .contentType(MediaType.APPLICATION_JSON).content(productString);
        

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        JSONAssert.assertEquals(exampleProductJsonUpdated, result.getResponse().getContentAsString(), false);

    }
    
    @Test
    public void deleteProduct() throws Exception {

    	doNothing().when(productService).deleteProduct("bingye", Long.valueOf(10001));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/accounts/bingye/products/10001");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());

    }

}
