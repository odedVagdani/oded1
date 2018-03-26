package com.example.demo.DBDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DAO.CustomerDAO;
import com.example.demo.Entities.Company;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponRepo;
import com.example.demo.Entities.CouponType;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.CustomerRepo;
import com.example.demo.Entities.EnumFacade;
import com.example.demo.Exception.CouponAllReadyExistException;
import com.example.demo.Exception.CustomerIsNotExistExeption;
import com.example.demo.Exception.CustomerIsNotExistExeption;
/**
 * CustomerDBDAO implements CustomerDAO 
 * @author oded
 * all the method to control customer user from CustomerFacade. 
 */
@Component
public class CustomerDBDAO implements CustomerDAO
{

	private Customer loggedInCustomer;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private CouponRepo couponRepo;
	@Autowired
	private  TransactionsDBDAO transactionsDbdao;
	
	/**
	 * method to create a customer
	 * @param c - created customer 
	 */
	@Override
	public void createCustomer(Customer c) {
		customerRepo.save(c);
	
	}
	
	/**
	 * method to remove customer
	 * @param c - removed customer
	 */
	
	@Override
	public void removeCustomer(Customer c) {
		customerRepo.delete(c);
		
	}

	/**
	 * method to update customer password and list of coupons
	 * @param c - updated customer
	 */
	@Override
	public void updateCustomer(Customer c) throws CustomerIsNotExistExeption{
		
		Customer tempCustomer = customerRepo.findCustomerById(c.getId());
		tempCustomer.setPassword(c.getPassword());
		tempCustomer.setCoupons(c.getCoupons());
	
		customerRepo.save(tempCustomer);
	}

	/**
	 * method that get an id and return customer from DB.
	 * @param id - customer id
	 * @return - customer object
	 */
	@Override
	public Customer getCustomer(int id) {
		
		
		return customerRepo.findCustomerById(id);
	}

	/**
	 * method to get all customers from DB.
	 * @return Collection <Customer> of customer objects
	 */
	@Override
	public ArrayList getAllCustomers() {
		return (ArrayList) customerRepo.findAll();
	}

	/**
	 * method to get all the coupons of the customer that logged in to the coupons system
	 * @return - Collection <Coupon> of coupons objects
	 */
	@Override
	public ArrayList getCoupons() {
		transactionsDbdao.writeToTableCustomer("getCoupons", true, EnumFacade.CustomerFacade, loggedInCustomer.toString());
		return (ArrayList) loggedInCustomer.getCoupons();
	}
	
	public Coupon getCouponById(int id){
		
		return couponRepo.findCouponById(id);
	}

	/**
	 * method to logged in to coupons system 
	 * @param customer_name - user name
	 * @param password - user password
	 * @return
	 */
	@Override
	public boolean login(String customer_name, String password) {

		loggedInCustomer = customerRepo.findCustomerByNameAndPassword(customer_name, password);  
		if (loggedInCustomer!=null ){
			
			return true;	
		}
		
		return false; 
	}
	/**
	 * method to logged in to coupons system 
	 * @param customer_name - user name
	 * @param password - user password
	 * @return
	 */
	public Customer getCustomerByName(String name){
		 return customerRepo.findCustomerByCustomerName(name);
	}
	
	/**
	 * method to logged in to coupons system 
	 * @param customer_name - user name
	 * @param password - user password
	 * @return
	 */
	public void buyCoupon(Coupon coupon){
	
		if(customerRepo.CustomerContainCoupon(loggedInCustomer.getId(), coupon.getId())!=null){
			transactionsDbdao.writeToTable("purchaseCoupon", false, EnumFacade.CustomerFacade);
			throw new CouponAllReadyExistException("you can buy a coupon only once");
		}
		
		coupon.setAmount(coupon.getAmount()-1);
		this.loggedInCustomer.getCoupons().add(coupon);
		customerRepo.save(loggedInCustomer);
		couponRepo.save(coupon);
		transactionsDbdao.writeToTableCustomer("purchaseCoupon", true, EnumFacade.CustomerFacade, loggedInCustomer.toString());
		
		
	}
	/**
	 * method to logged in to coupons system 
	 * @param customer_name - user name
	 * @param password - user password
	 * @return
	 */
	public Coupon getCouponByIdAndTimeAvailable(Coupon coupon){
		Date today = Calendar.getInstance().getTime();
		return couponRepo.findCouponByIdAndTime(coupon.getId(), today);
	}
	/**
	 * method to logged in to coupons system 
	 * @param customer_name - user name
	 * @param password - user password
	 * @return
	 */
	public List getAllPurchaseCouponsByType( CouponType type){
		
		transactionsDbdao.writeToTableCustomer("getAllPurchaseCouponsByType", true, EnumFacade.CustomerFacade, loggedInCustomer.toString());
		return customerRepo.findAllCouponFromCusttomerByType(loggedInCustomer.getId(), type);
	}
	/**
	 * method to logged in to coupons system 
	 * @param customer_name - user name
	 * @param password - user password
	 * @return
	 */
	public List getAllPurchaseCouponByPrice ( double price){
		
		transactionsDbdao.writeToTableCustomer("getAllPurchaseCouponByPrice", true, EnumFacade.CustomerFacade, loggedInCustomer.toString());
		return customerRepo.findAllPurchaseCouponsByPrice(loggedInCustomer.getId(), price);
	}
}