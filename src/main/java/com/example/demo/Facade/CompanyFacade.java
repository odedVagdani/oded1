package com.example.demo.Facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.DBDAO.TransactionsDBDAO;
import com.example.demo.Entities.Company;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponType;
import com.example.demo.Exception.CompanyIsNotExistExeption;
import com.example.demo.Exception.CouponAllReadyExistException;
import com.example.demo.Exception.CouponIsNotAvailableExeption;
import com.example.demo.Exception.CouponIsNotExistExeption;
import com.example.demo.Exception.CouponIsNotAvailableExeption;
import com.example.demo.Exception.CouponTitleAllreadyExistException;
import com.example.demo.Exception.PasswordNotCorrectException;
import com.example.demo.Exception.PriceCantBeMinusException;
import com.example.demo.Exception.UserNameNotMatchException;
/**
 * class the manage all the options to user from company type
 * @author oded
 *
 */
@Component
public class CompanyFacade implements CouponClientFacade{

	@Autowired
	private CompanyDBDAO companyDbdao;
	@Autowired
	private CustomerDBDAO customerDbdao;
	@Autowired
	private CouponDBDAO couponDbdao;
	@Autowired
	private TransactionsDBDAO transactionsDbdao;
	

	/**
	 * login method for compay
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
			
		if (companyDbdao.login(name, password) == true && clientType == ClientType.Company  ){
			return this;
		}
		return null;
	}

	public CompanyFacade(){
		
	}
	/**
	 * method that give to the company ability to create a new coupon
	 * @param c
	 * @throws CouponAllReadyExistException
	 * @throws CouponTitleAllreadyExistException
	 * @throws InterruptedException
	 */
	public void createCoupon(Coupon c) throws CouponAllReadyExistException ,CouponTitleAllreadyExistException, InterruptedException{
		if (couponDbdao.getCoupon(c.getId())!=null){
			throw new CouponAllReadyExistException("the coupon all ready exist");	
		}
		if (couponDbdao.getCouponByTitle(c.getTitle())!=null){
			throw new CouponTitleAllreadyExistException("coupon title all ready exist , please change");
		}
		else {
			couponDbdao.createCoupon(c);
			companyDbdao.getLastCompany().getCoupons().add(c);
			companyDbdao.updateCompany(companyDbdao.getLastCompany());
			
		}
	}
	/**
	 * method that give to the company ability to remove a coupon
	 * @param c
	 * @throws CouponIsNotExistExeption
	 */
	public void removeCoupon(Coupon c) throws CouponIsNotExistExeption{
		
		if(couponDbdao.getCoupon(c.getId())!=null){
			couponDbdao.removeCoupon(c);
		}
		else throw new CouponIsNotExistExeption("the coupon doesnt exist");
		
	}
	
	/**
	 * method that give to the company ability to update a coupon
	 * @param c
	 * @throws CouponIsNotExistExeption
	 */
	public void updateCoupon(Coupon c)throws CouponIsNotExistExeption{
		if(couponDbdao.getCoupon(c.getId())==null){
			
			throw new CouponIsNotExistExeption("coupon doesnt exist");
		}
		
		couponDbdao.updateCoupon(c);
	}
	
	/**
	 *method that show to the user company by is request from DB
	 * @param id
	 * @return
	 * @throws CompanyIsNotExistExeption
	 * @throws InterruptedException
	 */
	public Company getCompany(int id)throws CompanyIsNotExistExeption, InterruptedException{
		
		if(companyDbdao.getCompany(id)==null){
			throw new CompanyIsNotExistExeption ( "company doesnt exist");
		}
		
		return  companyDbdao.getCompany(id);
	}
	
	/**
	 * method that show to the user list of coupon from DB
	 * @return
	 * @throws CouponIsNotExistExeption
	 * @throws InterruptedException
	 */
	public Collection getAllCoupons() throws CouponIsNotExistExeption, InterruptedException{
		
		if(companyDbdao.getCoupons().isEmpty()) {
			throw new CouponIsNotExistExeption("thre is no coupons ");
		}
		 return companyDbdao.getCoupons();
	}
	
	/**
	 * method that show to user list of coupon from DB by coupon type
	 * @param type
	 * @return
	 * @throws CouponIsNotExistExeption
	 */
	public Collection getCouponsByType(CouponType type)throws CouponIsNotExistExeption{
		
		
		if(companyDbdao.getCouponsByType(type).isEmpty()){
			throw new CouponIsNotExistExeption ( "Coupons doesnt exist");
		}
		
		return companyDbdao.getCouponsByType( type);
		
	}
	
/**
 * method that show to user list of coupon from DB by coupon price
 * @param price
 * @return
 * @throws PriceCantBeMinusException
 * @throws CouponIsNotExistExeption
 */
	public Collection getCouponsByPrice(int price) throws PriceCantBeMinusException, CouponIsNotExistExeption{
		if (price < 0 ){
			throw new PriceCantBeMinusException("price need to be greater than 0");
		}
		if(companyDbdao.getCouponsByPrice(price).isEmpty()){
			throw new CouponIsNotExistExeption ( "Coupons doesnt exist check the price");
		}
		return companyDbdao.getCouponsByPrice( price);
		
	}
	
	/**
	 * method that show to user list of coupon from DB by coupon date
	 * @return
	 * @throws CouponIsNotAvailableExeption
	 */
	public Collection getCouponsByDate()throws CouponIsNotAvailableExeption{
		
		if(companyDbdao.getCouponsByDate().isEmpty()){
			throw new CouponIsNotAvailableExeption ( "there is no coupons that match this date");
	
		}
		
		return companyDbdao.getCouponsByDate();
		
	}

	
}

