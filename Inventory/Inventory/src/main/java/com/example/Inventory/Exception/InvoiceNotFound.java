package com.example.Inventory.Exception;

public class InvoiceNotFound extends RuntimeException{
	private static final long serial=1L;
	private int ErrorCode;
	public InvoiceNotFound(String ErrorMsg,int errorCode){	
		super(ErrorMsg);
		ErrorCode=errorCode;
	}
}
