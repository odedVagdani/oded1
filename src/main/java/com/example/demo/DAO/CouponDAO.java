package com.example.demo.DAO;

import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponType;
/**
 * interface class that defines methods to DBDAO classes.
 * @author oded
 *
 */

@Component
public interface CouponDAO {
	
	/**
	 * method to create coupon
	 * @param c - the created coupon
	 */
	void createCoupon (Coupon c);
	/**
	 * method to remove coupon
	 * @param c - the removed coupon
	 */
	void removeCoupon (Coupon c);
	/**
	 * method to update the price and end date of the coupon.
	 * @param c - updated coupon
	 */
	void updateCoupon (Coupon c);
	/**
	 * method that get a id and return coupon from DB.
	 * @param id - coupon id
	 * @return - coupon
	 */
	Coupon getCoupon (int id);
	/**
	 * method that return all coupons from DB.
	 * @return - list of coupons
	 */
	Collection getAllCoupons();
	/**
	 * method that get type of coupon and return coupon by this type.
	 * @param type - type of the coupon
	 * @return - coupon
	 */
	Collection getCouponByType(CouponType type);
	/**
	 * Delete coupon who's date is expired
	 * @param today today's date
	 */
//	void deleteExpiredCoupons(Date today);
	

}