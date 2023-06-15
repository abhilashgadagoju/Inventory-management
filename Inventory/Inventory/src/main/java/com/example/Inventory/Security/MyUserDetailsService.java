package com.example.Inventory.Security;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.Inventory.model.InventoryUser;
import com.example.Inventory.repository.InventoryUserRepo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    InventoryUserRepo inventoryUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
	System.out.println("in loadUserByUsername");
	InventoryUser myuser=inventoryUserRepo.findByEmail(username);
	List<GrantedAuthority> auth = new  ArrayList<GrantedAuthority>();
	for(String role : myuser.getRole()) {
	    auth.add(new SimpleGrantedAuthority(role.trim()));
	}
	UserDetails user = User.builder()
                		.username(myuser.getUsername())
                		.password(myuser.getPassword())
                		.authorities(auth)
                		.build();
	System.out.println("in loadUserByUsername end");
	return user;
    }



}
