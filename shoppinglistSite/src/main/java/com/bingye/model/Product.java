package com.bingye.model;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {
	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private String title;
	private String description;
	private String price;

	public Product() {

	}
	


	public Product(long id, String username,String title, String description, String price) {
		super();
		this.id = id;
		this.username = username;
		this.title = title;
		this.description = description;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}

	
	

}