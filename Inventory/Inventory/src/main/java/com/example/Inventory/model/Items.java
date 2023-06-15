package com.example.Inventory.model;

import java.sql.Date;

public class Items {
	private String name;
	private int quntity;
	private int price;
	public String getName() {
	    return name;
	}
	public void setName(String name) {
	    this.name = name;
	}
	public int getQuntity() {
	    return quntity;
	}
	public void setQuntity(int quntity) {
	    this.quntity = quntity;
	}
	public int getPrice() {
	    return price;
	}
	public void setPrice(int price) {
	    this.price = price;
	}
	
}
