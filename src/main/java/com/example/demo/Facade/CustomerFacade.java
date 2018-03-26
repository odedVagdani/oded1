package com.example.demo.Facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.DBDAO.TransactionsDBDAO;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponType;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.EnumFacade;
import com.example.demo.Exception.CouponAllReadyExistException;
import com.example.demo.Exception.CouponIsNotExistExeption;
import com.example.demo.Exception.CouponIsNotAvailableExeption;
/**
 * class the manage the customer options in the system
 * @author oded
 *
 */
@Component
public class CustomerFacade implements CouponClientFacade{

	
	@Autowired
	private CustomerDBDAO customerDbdao;
	@Autowired
	private TransactionsDBDAO transactionsDbdao;
	
	
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {

		if (customerDbdao.login(name, password) == true && clientType == ClientType.Customer) { 
			transactionsDbdao.writeToTable("login", true, EnumFacade.CustomerFacade);
		
			return this;	 
		}
		
		return null;
	}

	
	public void CustomerFacade(){
		
	}
	
	/**
	 * show to the user the purchase of coupon by id from the DB
	 * @param c
	 * @throws CouponIsNotExistExeption
	 * @throws CouponIsNotAvailableExeption
	 */
	public void purchaseCoupon(Coupon c)throws CouponIsNotExistExeption , CouponIsNotAvailableExeption{
		
		if (customerDbdao.getCouponById(c.getId())==null || c.getAmount()<=0){ 
			transactionsDbdao.writeToTable("purchaseCoupon", false, EnumFacade.CustomerFacade);
			throw new CouponIsNotExistExeption("the coupon out of stok or doesnt exist");
		}
		if (customerDbdao.getCouponByIdAndTimeAvailable(c)==null){
			transactionsDbdao.writeToTable("purchaseCoupon", false, EnumFacade.CustomerFacade);
			throw new CouponIsNotAvailableExeption("sorry the coupon end date ended"); 
		}
		
		customerDbdao.buyCoupon( c);
	}
	
	/**
	 * show to the user a list of all purchase of coupon from the DB
	 * @return list of coupons
	 */
	public Collection getAllPurchaseCoupons(){
		
		return customerDbdao.getCoupons();
	}
	
	/**
	 * show to the user a list of all purchase of coupon from the DB by type
	 * @param type
	 * @return list of coupons
	 */
	public List getAllPurchaseCouponsByType( CouponType type){
		
		return customerDbdao.getAllPurchaseCouponsByType(type);
	}
	
	/**
	 * show to the user a list of all purchase of coupon from the DB by price
	 * @param price
	 * @return list of coupons
	 */
	public Collection getAllPurchaseCouponsByPrice(double price){
		
		return customerDbdao.getAllPurchaseCouponByPrice( price);
	}
	
	
}