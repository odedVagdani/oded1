package com.example.demo.Entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/*
 * @Author: oded
 * the interface that send queshtion to the DB
 */
public interface CompanyRepo extends CrudRepository <Company,Integer>{
	
	/**
	 * find company from DB by id
	 * @param id
	 * @return company
	 */
	Company findCompanyById(int id);
	/**
	 * find company from DB by name
	 * @param name
	 * @return company
	 */
	Company findCompanyByCompanyName(String name);
	/**
	 *find coupons of comapnies from DB by id and price
	 * @param comp_id
	 * @param price
	 * @return list of companies
	 */
	@Query ("SELECT COUPONS FROM COMPANIES COMP INNER JOIN COMP.coupons AS COUPONS WHERE COMP.id = :id AND COUPONS.price <= :price) ")
	List<Coupon> getAllCouponsByPrice(@Param("id")int comp_id,@Param("price")double price);
	/**
	 * find coupons of comapnies from DB by id and type
	 * @param comp_id
	 * @param type
	 * @return list of companies
	 */
	@Query ("SELECT COUPONS FROM COMPANIES COMP INNER JOIN COMP.coupons AS COUPONS WHERE COMP.id = :id AND COUPONS.type =:type")
	List<Coupon> getCouponsByType(@Param("id")int comp_id,@Param("type")CouponType type);
    /**
     * find coupons of comapnies from DB by id and date
     * @param comp_id
     * @param date
     * @return list of companies
     */
	@Query ("SELECT COUPONS FROM COMPANIES COMP INNER JOIN COMP.coupons AS COUPONS WHERE COMP.id = :id AND COUPONS.endDate >=:date  ")
	List<Coupon> getCouponsByDate(@Param("id")int comp_id,@Param("date")LocalDate date);
	/**
	 * find comapnies from DB by name and password
	 * @param company_name
	 * @param company_password
	 * @return company
	 */
	@Query ("SELECT c FROM COMPANIES c WHERE c.companyName = :company_name AND c.password = :company_password")
	Company findCompanyByNameAndPassword(@Param ("company_name") String company_name , @Param("company_password")String company_password);

	
	
}