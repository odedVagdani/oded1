package com.example.demo.Exception;

public class CustomerIsNotExistExeption extends RuntimeException{
	public CustomerIsNotExistExeption(String message){
		super(message);
	}

}