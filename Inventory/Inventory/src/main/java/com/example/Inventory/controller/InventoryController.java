package com.example.Inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Inventory.Security.MyUserDetailsService;
import com.example.Inventory.data.LoginRequest;
import com.example.Inventory.data.TokenResponse;
import com.example.Inventory.model.InventoryUser;
import com.example.Inventory.model.Invoice;
import com.example.Inventory.repository.InventoryUserRepo;
import com.example.Inventory.service.InventoryService;
import com.example.Inventory.util.MyJWTutil;

@RestController
public class InventoryController {
	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	MyJWTutil jWTutil;
	@Autowired
	MyUserDetailsService myUserDetailsService;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	InventoryUserRepo inventoryUserRepo;
	
	@GetMapping("/{billId}")
	ResponseEntity<Invoice> getAllInventory(@PathVariable("billId") int id ){
		System.out.println(id+",getAllInventory");
		return new ResponseEntity<Invoice>(inventoryService.getInvoice(id),HttpStatus.OK);
	}
	
	@PostMapping("/")
	ResponseEntity<Invoice> createBill(@RequestBody Invoice bill ){
		System.out.println(bill+",");
		return ResponseEntity.ok(inventoryService.save(bill));
	}
	
	@PostMapping("/authenticate")
	ResponseEntity<TokenResponse> getToken(@RequestBody LoginRequest request){
	    System.out.println(" in /authenticate");
	    InventoryUser user =inventoryUserRepo.findByEmail(request.getUsername());
	    authenticationManager.authenticate(
	            new
	            UsernamePasswordAuthenticationToken(request.getUsername(),
	            request.getPassword())
	         );
	    
		String token=jWTutil.genarateToken(request.getUsername(),user.getUsername());
		System.out.println(token);
		TokenResponse tokenResponse= new TokenResponse(token);
		return new ResponseEntity<TokenResponse>(tokenResponse,HttpStatus.OK);
	}
	
	
}
