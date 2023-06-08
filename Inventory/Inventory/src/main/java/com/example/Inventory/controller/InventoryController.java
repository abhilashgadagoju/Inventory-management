package com.example.Inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Inventory.model.Invoice;
import com.example.Inventory.service.InventoryService;

@RestController
public class InventoryController {
	@Autowired
	InventoryService inventoryService;
	
	@GetMapping("/{billId}")
	ResponseEntity<Invoice> getAllInventory(@PathVariable("billId") int id ){
		System.out.println(id+",");
		return new ResponseEntity<Invoice>(inventoryService.getInvoice(id),HttpStatus.OK);
	}
	
	@PostMapping("/")
	ResponseEntity<Invoice> createBill(@RequestBody Invoice bill ){
		System.out.println(bill+",");
		return ResponseEntity.ok(inventoryService.save(bill));
	}
	
	
}
