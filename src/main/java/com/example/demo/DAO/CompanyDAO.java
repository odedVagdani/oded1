package com.example.demo.DAO;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.example.demo.Entities.Company;
import com.example.demo.Entities.Coupon;

@Component
/**
 * interface class that defines methods to DBDAO classes.
 * @author oded
 */
public interface CompanyDAO {
	/**
	 * method that create a company and saved in DB.
	 * @param c - is the company that method create.
	 * @throws InterruptedException 
	 */
	void createCompany (Company c) throws InterruptedException;
	/**
	 * method that remove a company and saved in DB.
	 * @param c - is the company that the method remove.
	 * @throws InterruptedException 
	 */
	void removeCompany ( Company c) throws InterruptedException;
	/**
	 * method to update a company. the method update the coupons list, the password and the email.
	 * @param c - updated company
	 * @throws InterruptedException 
	 */
	void updateCompany (Company c) throws InterruptedException;
	/**
	 * method that get an id and return a company.
	 * @param id - company id
	 * @return - company
	 * @throws InterruptedException 
	 */
	Company getCompany(int id) throws InterruptedException;
	/**
	 * method to get all the companies in coupon system.
	 * @return - list of companies.
	 * @throws InterruptedException 
	 */
	Collection getAllCompanies() throws InterruptedException;
	/**
	 * method to get all coupons from the last clientType.company that logged in
	 * @return - list of coupons of clientType.company
	 * @throws InterruptedException 
	 */
	Collection getCoupons() throws InterruptedException;
	/**
	 * logged in to coupons system.
	 * @param company_name - name of the company user
	 * @param password - password of the user
	 * @return 
	 */
	boolean login (String company_name , String password );
	

}