package com.example.Inventory.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.Inventory.model.InventoryUser;
import com.example.Inventory.repository.InventoryUserRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
//@Scope("prototype")
public class MyJWTutil {
    
    	@Autowired(required=true)
    	private InventoryUserRepo inventoryUserRepo;
    	private String UserPassword;
	private String secret_key="abhilahsgkjhasdfjlkjk=lsdj=lskjd=alksdjf=lakjdf=alkdfsj";
	
	/*@Bean
	MyJWTutil getMyJWTutil() {
	    return new MyJWTutil();
	}*/
	public String getUserPassword(){
	    return UserPassword;
	}
	public List<GrantedAuthority> getUserAuthorites(String strUserName) {
	    InventoryUser myuser=inventoryUserRepo.findByEmail(strUserName);
		List<GrantedAuthority> auth = new  ArrayList<GrantedAuthority>();
		for(String role : myuser.getRole()) {
		    auth.add(new SimpleGrantedAuthority(role.trim()));
		}
		return auth;
	}
	
	public boolean validateToken(String token) {
	    Claims claims = getClaims(token);
	    String TokenUsername=(String) claims.get("userId");
	    String TokenEmail = (String) claims.get("email");
	    System.out.println("TokenUserName,"+TokenUsername+"TokenEmail,"+TokenEmail);
	    int TokenExpireTime = (int) claims.get("exp");
	    
	    InventoryUser inventoryUser=inventoryUserRepo.findByEmail(TokenEmail);
	    //UserPassword=inventoryUser.getPassword();
	    if(inventoryUser!=null && inventoryUser.getUsername().equalsIgnoreCase(TokenUsername)) {
		Date tokenDate = new Date(TokenExpireTime);
		Date currentDate = new Date();
		System.out.println("token in millisec"+TokenExpireTime+"token date"+tokenDate+"current Date,"+currentDate);
		
		if(tokenDate.before(currentDate)) {
		    return true;
		}else {
		    System.out.println("else expire");
		}
	    }
	    return false;
	}
	
	public Claims getClaims(String token) {
	    Claims claims = Jwts.parser()
	            .setSigningKey(secret_key)
	            .parseClaimsJws(token)
	            .getBody();
	    return claims;
	}

	
	public String getUsernameFromToken(String token) {
	    Claims claims = getClaims(token);
	    System.out.println("in getUsernameFromToken userID:"+(String) claims.get("userId"));
	    return (String) claims.get("userId");
	}
	
	public String getEmailFromToken(String token) {
	    Claims claims = getClaims(token);
	    System.out.println("in getUsernameFromToken userID:"+(String) claims.get("userId"));
	    return (String) claims.get("email");
	}
	
	
	
	
	
	public String genarateToken(String strEmail,String strUserName) {
		Date now = new Date();
		byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
		String base64Key = Base64.getEncoder().encodeToString(keyBytes);
		System.out.println("Key:"+Keys.secretKeyFor(SignatureAlgorithm.HS256));
		System.out.println("base64Key:"+base64Key);
		
		Claims claims = Jwts.claims();

        // Add custom claims
        claims.put("userId",strUserName);
        claims.put("email", strEmail);
        String token = Jwts.builder()
				.setClaims(claims)
				.setIssuer("Inventory")
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime()+(5*24*60*60*1000)))
				.signWith(SignatureAlgorithm.HS256, secret_key)
				.compact();
        
		return token;

	}

}
