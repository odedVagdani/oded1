package com.example.demo.DBDAO;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DAO.CompanyDAO;
import com.example.demo.Entities.Company;
import com.example.demo.Entities.CompanyRepo;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponType;
import com.example.demo.Exception.CompanyIsNotExistExeption;
import com.example.demo.connection.Pool;
import com.example.demo.connection.SyncObject;
/**
 * CompanyDBDAO implements CompanyDAO 
 * @author oded
 * all the method to control company user from CompanyFacade. 
 */
@Component
public class CompanyDBDAO implements CompanyDAO{
	
	
	private Company loggedInCompany;
	@Autowired
	private CompanyRepo companyRepo;
	@Autowired
	private TransactionsDBDAO transactionsDbdao;
	
	/**
	 * a list to save all companies. 
	 */
	ArrayList<Company> listOfCompanies =new ArrayList<Company>();
	
	/**
	 * an empty constructor
	 */
	public CompanyDBDAO(){
		
	}
	/**
	 * method that create a company and saved in DB.
	 * @param c - is the company that method create.
	 * @throws InterruptedException 
	 */
	@Override
	public void createCompany(Company c) throws InterruptedException {
		SyncObject syncObject = Pool.getInstance().getConnection();
		companyRepo.save(c);
		Pool.getInstance().returnConenction(syncObject);
	}
	/**
	 * method to get company object by the company name
	 * @param name - String company name
	 * @return - company object
	 * @throws InterruptedException 
	 */
	public Company getCompanyByName (String name) throws InterruptedException{
		SyncObject syncObject = Pool.getInstance().getConnection();
		Company company = companyRepo.findCompanyByCompanyName(name);
		Pool.getInstance().returnConenction(syncObject);
		return company;
		}
	
	/**
	 * method that remove a company and saved in DB.
	 * @param c - is the company that the method remove.
	 * @throws InterruptedException 
	 */

	@Override
	public void removeCompany(Company c) throws InterruptedException {
		SyncObject syncObject = Pool.getInstance().getConnection();
		companyRepo.delete(c);
		Pool.getInstance().returnConenction(syncObject);
	}

	/**
	 * method to update a company. the method update the coupons list, the password and the email.
	 * @param c - updated company
	 * @throws InterruptedException 
	 */
	@Override
	public void updateCompany(Company c) throws InterruptedException{
		SyncObject syncObject = Pool.getInstance().getConnection();
		Company tempCompany = companyRepo.findCompanyByCompanyName(c.getCompanyName());
		tempCompany.setCoupons(c.getCoupons());
		tempCompany.setEmail(c.getEmail());
		tempCompany.setPassword(c.getPassword());
		
		companyRepo.save(tempCompany);
		Pool.getInstance().returnConenction(syncObject);
	}

	/**
	 * method that get an id and return a company.
	 * @param id - company id
	 * @return - company object
	 * @throws InterruptedException 
 	 */
	@Override
	public Company getCompany(int id) throws InterruptedException {
		SyncObject syncObject = Pool.getInstance().getConnection();
		Company companyID = companyRepo.findCompanyById(id);
		Pool.getInstance().returnConenction(syncObject);
		return companyID;
	}

	/**
	 * method to get all the companies in coupon system.
	 * @return - list of companies object.
	 * @throws InterruptedException 
	 */
	@Override
	public Collection<Company> getAllCompanies() throws InterruptedException {
		SyncObject syncObject = Pool.getInstance().getConnection();
		ArrayList<Company> allCompanies = (ArrayList<Company>) companyRepo.findAll();
		Pool.getInstance().returnConenction(syncObject);
		return allCompanies;
	}

	/**
	 * method to get all coupons from the last clientType.company that logged in
	 * @return - list of coupons of clientType.company
	 * @throws InterruptedException 
	 */
	@Override
	public Collection getCoupons() throws InterruptedException {
		
		return loggedInCompany.getCoupons();
	
	}
/////////////until here connection pool
	/**
	 * logged in to coupons system.
	 * @param company_name - name of the company user
	 * @param password - password of the user
	 * @return
	 */
	@Override
	public boolean login(String company_name, String password) {
		
		if (companyRepo.findCompanyByNameAndPassword(company_name, password)!=null ){
			loggedInCompany = companyRepo.findCompanyByNameAndPassword(company_name,password);
			
			return true ;
		}
		
		return false;
	}

	/**
	 * method to get coupon by specific type from the logged in company user
	 * @param type
	 * @return - List of coupons objects
	 */
	public List getCouponsByType(CouponType type){
		
		return companyRepo.getCouponsByType(loggedInCompany.getId(), type);
		
	}
	
	/**
	 * method to get specific coupons up to a certain price from the logged in company user 
	 * @param price - price
	 * @return - List of coupons objects
	 */

		
	public List getCouponsByPrice ( double price )	{
		
		return companyRepo.getAllCouponsByPrice(loggedInCompany.getId(), price);
	}
	
	/**
	 * method to get the available coupons from the logged in company user
	 * @return - List of coupons objects
	 */

	public List getCouponsByDate()	{
		
//		Date today = Calendar.getInstance().getTime();LocalDate.parse("2022-03-11"), 
		return companyRepo.getCouponsByDate(loggedInCompany.getId(), LocalDate.now());
		
	}
		
	public Company getLastCompany(){
		return loggedInCompany;
		
	}

}