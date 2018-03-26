package com.example.demo.Exception;

public class UserNameNotMatchException extends RuntimeException {
	
	public UserNameNotMatchException (String massage){
		
		super(massage);
	}

}