package com.example.Inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Inventory.model.Invoice;
import com.example.Inventory.repository.InventoryRepo;
import com.example.Inventory.repository.InventoryUserRepo;

@Service
public class InventoryService {
	
	@Autowired
	InventoryRepo inventoryRepository;
	
	

	public Invoice save(Invoice bill) {
		System.out.println(bill.toString()+",save");
		return inventoryRepository.save(bill);
	}

	public Invoice getInvoice(int id) {
		System.out.println(inventoryRepository.findAll()+",getInvoice");
		return inventoryRepository.findByInvoice(id);
	}
	
	

}
