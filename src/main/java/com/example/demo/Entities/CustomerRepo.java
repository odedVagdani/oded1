package com.example.demo.Entities;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
/*
 * @Author: oded
 * the interface that send queshtion to the DB
 */
public interface CustomerRepo extends CrudRepository<Customer , Integer>  {
	
	
	Customer findCustomerById(int id);
	
	Customer findCustomerByCustomerName(String name);
	
	
	@Query ("SELECT coup FROM CUSTOMERS cust INNER JOIN cust.coupons AS coup WHERE cust.id = :customerId AND coup.type = :type") 
	List <Coupon> findAllCouponFromCusttomerByType(@Param("customerId")int cust_id,@Param("type") CouponType couponType);
	
	
	@Query ("SELECT coup FROM CUSTOMERS cust INNER JOIN cust.coupons AS coup WHERE cust.id = :customerId AND coup.price <= :price") 
	List <Coupon> findAllPurchaseCouponsByPrice(@Param("customerId")int cust_id,@Param("price") double price);
	
	@Query ("SELECT c FROM CUSTOMERS c WHERE c.customerName = :customer_name AND c.password = :customer_pass")
	Customer findCustomerByNameAndPassword(@Param("customer_name") String customer_name , @Param("customer_pass")String cust_password);

	@Query ("SELECT coup FROM CUSTOMERS cust INNER JOIN cust.coupons AS coup WHERE cust.id = :customerId AND coup.id = :coupId")
	Coupon CustomerContainCoupon (@Param("customerId")int customerId,@Param("coupId") int coupId);
	
}