package com.example.demo.Exception;

public class NameAllreadyExistException  extends  RuntimeException{

	public NameAllreadyExistException(String message){
		super(message);
	}
}