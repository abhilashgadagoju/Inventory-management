package com.example.Inventory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.Inventory.model.InventoryUser;
@Repository
public interface InventoryUserRepo extends MongoRepository<InventoryUser, String>{
    
    InventoryUser findByUsername(String username);
    
    InventoryUser findByEmail(String username);
    

}
