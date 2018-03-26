package com.example.demo.Exception;

public class CompanyIsIntoDbAllreadyException   extends  RuntimeException{

	public CompanyIsIntoDbAllreadyException (String message){
		super(message);
	}
}