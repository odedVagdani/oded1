package com.example.demo.Exception;

public class PriceCantBeMinusException extends  RuntimeException{
	
	public PriceCantBeMinusException ( String message){
		super (message);
	}

}