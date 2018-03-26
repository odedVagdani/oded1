package com.example.demo.Entities;

import java.time.LocalDate;
import java.util.Date;



import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
/*
 * @Author: oded
 * the interface that send queshtion to the DB
 */
public interface CouponRepo extends CrudRepository<Coupon, Integer>{
    /**
     * find coupons from DB by title
     * @param title
     * @return coupons
    */
	Coupon findCouponByTitle(String title);
	
	/**
	 * find coupons from DB by id
	 * @param id
	 * @return coupon
	 */
	Coupon findCouponById(int id);
	
	/**
	 * find coupons from DB by id and date
	 * @param couponId
	 * @param today
	 * @return coupon
	 */
	@Query ("SELECT c FROM COUPONS c WHERE c.id = :couponId AND c.endDate >= :buyDate")
	Coupon findCouponByIdAndTime (@Param("couponId") int couponId , @Param ("buyDate") Date today);

	/**
	 * This method deletes all coupons which there endDate is before current date 
	 * @param today - thats the current
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM COUPONS c WHERE c.endDate <= :coupon_endDate")
	void deleteCouponByEndDate (@Param("coupon_endDate") Date endDate);
	

}
	



