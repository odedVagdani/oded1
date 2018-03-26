package com.example.demo.Exception;

public class CouponIsNotExistExeption extends  RuntimeException{
	public CouponIsNotExistExeption(String message){
		super(message);
	}

}