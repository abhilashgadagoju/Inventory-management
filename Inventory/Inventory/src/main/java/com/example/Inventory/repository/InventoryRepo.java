package com.example.Inventory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.Inventory.model.Invoice;


public interface InventoryRepo extends MongoRepository<Invoice, String> {

    Invoice findByInvoice(int id);

}
