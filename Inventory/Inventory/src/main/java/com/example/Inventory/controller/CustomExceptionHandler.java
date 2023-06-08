package com.example.Inventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.Inventory.data.ErrorMessage;
import com.example.Inventory.Exception.InvoiceNotFound;

@RestControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(InvoiceNotFound.class)
	public ResponseEntity<ErrorMessage> InvoiceNotFoundException(InvoiceNotFound ex){
		ErrorMessage error = new ErrorMessage(404,ex.getMessage().toString());
		return ResponseEntity.ok(error);
	}

}
