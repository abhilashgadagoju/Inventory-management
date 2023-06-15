package com.example.Inventory.Security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Inventory.util.MyJWTutil;

@Component
public class JWTFilter  extends OncePerRequestFilter{
    
    
    @Autowired
    private MyJWTutil jWTutil;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {
	System.out.println("do filter token,");
	    
	String token = request.getHeader("Authorization");
	
	if(token!=null&&token.startsWith("Bearer ")) {
	   token=token.substring(7);
	   System.out.println("token,"+token);
	   if(jWTutil.validateToken(token)) {
	       System.out.println("Validate token true");
	       String tokenEmail=jWTutil.getEmailFromToken(token);
	       System.out.println("token username"+tokenEmail);
	       
	       UserDetails userDetails = myUserDetailsService.loadUserByUsername(tokenEmail);
	       UsernamePasswordAuthenticationToken authToken = 
		       new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
	       authToken.setDetails(new
		            WebAuthenticationDetailsSource().buildDetails(request));
	       SecurityContextHolder.getContext().setAuthentication(authToken);     
	   }
	}
	filterChain.doFilter(request, response);
	
    }

}
