package com.example.Inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.example.Inventory.model.Invoice;
import com.example.Inventory.repository.InventoryRepo;

@Service
public class InventoryService {
	
	@Autowired
	InventoryRepo inventoryRepository;

	public Invoice save(Invoice bill) {
		System.out.println(bill.toString()+",");
		return inventoryRepository.save(bill);
	}

	public Invoice getInvoice(int id) {
		System.out.println(inventoryRepository.findAll()+",");
		return inventoryRepository.findInventoryByInvoice(Integer.toString(id));
	}
	
	

}
