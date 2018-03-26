package com.example.demo.Exception;

public class CouponTitleAllreadyExistException extends  RuntimeException {
	public CouponTitleAllreadyExistException (String massage){
		super (massage);
	}
}