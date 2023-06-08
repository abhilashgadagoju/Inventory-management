package com.example.Inventory.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.Inventory.model.Invoice;

public interface InventoryRepo extends MongoRepository<Invoice,String>{

	Invoice findInventoryByInvoice(String id);
	
	
	List<Invoice> findAll();


	
}
