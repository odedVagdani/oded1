package com.example.demo.Exception;

public class CouponAllReadyExistException extends  RuntimeException{
	public CouponAllReadyExistException ( String massage){
		super (massage);
	}

}