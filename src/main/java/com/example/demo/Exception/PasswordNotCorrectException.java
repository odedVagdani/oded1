package com.example.demo.Exception;

public class PasswordNotCorrectException extends RuntimeException {

	public PasswordNotCorrectException(String massage){
		super(massage);
	}
}