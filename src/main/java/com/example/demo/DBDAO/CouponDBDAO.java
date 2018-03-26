package com.example.demo.DBDAO;

import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

import com.example.demo.DAO.CouponDAO;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponRepo;
import com.example.demo.Entities.CouponType;
import com.example.demo.Exception.CouponIsNotExistExeption;
/**
 *  CouponDBDAO implements CouponDAO 
 * @author oded
 *all the method to control coupon functions from the facades.
 */
@Component
public class CouponDBDAO implements  CouponDAO{
	@Autowired
	private CouponRepo couponRepo;
	/**
	 * method to create coupon
	 * @param c - the created coupon
	 */
	@Override
	public void createCoupon(Coupon c) {
		couponRepo.save(c);
		
	}

	/**
	 * method to remove coupon
	 * @param c - the removed coupon
	 */
	@Override
	public void removeCoupon(Coupon c) {
		couponRepo.delete(c);
		
	}

	/**
	 * method to update the price and end date of the coupon.
	 * @param c - updated coupon
	 */
	@Override
	public void updateCoupon(Coupon c) {		
		Coupon tempCoupon = new Coupon();
		tempCoupon = couponRepo.findCouponById(c.getId());
		tempCoupon.setEndDate(c.getEndDate());
		tempCoupon.setPrice(c.getPrice());
		couponRepo.save(tempCoupon);
	}

	/**
	 * method that get a id and return coupon from DB.
	 * @param id - coupon id
	 * @return - coupon
	 */
	@Override
	public Coupon getCoupon(int id) {
		Coupon c = couponRepo.findCouponById(id);
		return c;
	}

	/**
	 * method that return all coupons from DB.
	 * @return - list of coupons
	 */
	@Override
	public ArrayList getAllCoupons() {
		
		return (ArrayList) couponRepo.findAll();
	}

	/**
	 * method that get type of coupon and return coupon by this type.
	 * @param type - type of the coupon
	 * @return - coupon
	 */
	@Override
	public ArrayList getCouponByType (CouponType type) {
		return null;
	}

	/**
	 * method to get coupon by the title
	 * @param Title - coupon title
	 * @return coupon object
	 */
	public Coupon getCouponByTitle(String Title){
		return couponRepo.findCouponByTitle(Title);
	}
	/**
	 * method that delete every 24 hours unused coupons
	 */
	public void deleteExpiredCoupons (Date endDate)
	{
		couponRepo.deleteCouponByEndDate(endDate);
	}
}