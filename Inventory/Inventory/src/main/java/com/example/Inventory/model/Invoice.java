package com.example.Inventory.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("Inventory")
public class Invoice {
	@Id
	public String id;
	public int invoice;
	public ArrayList<Items> bill;
	public int totalPrice;
	
	
	public int getInvoice() {
		return invoice;
	}
	public void setInvoice(int invoice) {
		this.invoice = invoice;
	}
	public ArrayList<Items> getBill() {
		return bill;
	}
	public void setBill(ArrayList<Items> bill) {
		this.bill = bill;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	

}
