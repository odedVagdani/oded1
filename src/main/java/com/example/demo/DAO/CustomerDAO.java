package com.example.demo.DAO;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.example.demo.Entities.Customer;
/**
 * interface class that defines methods to DBDAO classes.
 * @author oded
 *
 */
@Component
public interface CustomerDAO {

	/**
	 * method to create a customer
	 * @param c - created customer 
	 */
	void createCustomer (Customer c);
	/**
	 * method to remove customer
	 * @param c - removed customer
	 */
	void removeCustomer (Customer c);
	/**
	 * method to update customer password and list of coupons
	 * @param c - updated customer
	 */
	void updateCustomer (Customer c);
	/**
	 * method that get an id and return customer from DB.
	 * @param id - customer id
	 * @return - customer object
	 */
	Customer getCustomer (int id);
	/**
	 * method to get all customers from DB.
	 * @return Collection <Customer> of customer objects
	 */
	Collection getAllCustomers();
	/**
	 * method to get all the coupons of the customer that logged in to the coupons system
	 * @return - Collection <Coupon> of coupons objects
	 */
	
	Collection getCoupons();
	/**
	 * method to logged in to coupons system 
	 * @param customer_name - user name
	 * @param password - user password
	 * @return
	 */
	boolean login (String customer_name ,String password);
	

}