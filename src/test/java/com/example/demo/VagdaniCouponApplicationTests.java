

package com.example.demo;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.DBDAO.TransactionsDBDAO;
import com.example.demo.Entities.Company;
import com.example.demo.Entities.CompanyRepo;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponRepo;
import com.example.demo.Entities.CouponType;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.CustomerRepo;
import com.example.demo.Exception.CompanyIsNotExistExeption;
import com.example.demo.Exception.CouponAllReadyExistException;
import com.example.demo.Exception.CouponIsNotAvailableExeption;
import com.example.demo.Exception.CouponIsNotExistExeption;
import com.example.demo.Exception.CouponTitleAllreadyExistException;
import com.example.demo.Exception.CustomerIsNotExistExeption;
import com.example.demo.Exception.NameAllreadyExistException;
import com.example.demo.Exception.PasswordNotCorrectException;
import com.example.demo.Exception.PriceCantBeMinusException;
import com.example.demo.Exception.UserNameNotMatchException;
import com.example.demo.Facade.AdminFacade;
import com.example.demo.Facade.ClientType;
import com.example.demo.Facade.CompanyFacade;
import com.example.demo.Facade.CustomerFacade;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VagdaniCouponApplicationTests {

	@Autowired
	CompanyRepo companyRepo; 
	@Autowired
	CouponRepo couponRepo;
	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	CompanyDBDAO companyDbdao;
	@Autowired
	AdminFacade adminFacade;
	@Autowired
	CustomerDBDAO customerDbdao;
	@Autowired
	CompanyFacade couponFacade;
	@Autowired
	CouponDBDAO couponDbdao;
	@Autowired
	CompanyFacade companyFacade;
	@Autowired
	CustomerFacade customerFacade;
	@Autowired
	TransactionsDBDAO transactionsDbdao;

	SimpleDateFormat newDate = new SimpleDateFormat("dd/MM/yyyy");
	// c.setEndDate(newDate.parse("9/02/20019"));
	// Date today = Calendar.getInstance().getTime();

	// adminFacade tests
	@DirtiesContext
	@Test(expected = PasswordNotCorrectException.class)
	public void loginPasswordNotCorrectException() {
		adminFacade.login("oded", "6129453a", ClientType.Admin);
	}

	@DirtiesContext
	@Test(expected = UserNameNotMatchException.class)
	public void loginUserNameNotMatchException() {
		adminFacade.login(" ", "3594216", ClientType.Admin);
	}

	@DirtiesContext
	@Test
	public void adminLogin() {
		assertNotNull(adminFacade.login("oded", "6129453", ClientType.Admin));
    }

	@DirtiesContext
	@Test
	public void createCompany() throws Exception, InterruptedException {
		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("google", "1111", "google@gmail.com");
		adminFacade.createCompany(company);
		Company DbCompany = new Company();
		DbCompany = companyRepo.findCompanyByCompanyName(company.getCompanyName());
		Assert.assertEquals(DbCompany, company);
	}

	@DirtiesContext
	@Test(expected = NameAllreadyExistException.class)
	public void createCompanyExeption() throws Exception, InterruptedException {
		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("google", "1111", "google@gmail.com");
		adminFacade.createCompany(company);

		adminFacade.createCompany(company);
	}

	@DirtiesContext
	@Test
	public void removeCompany() throws Throwable, InterruptedException {
		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("smartsell", "2222", "smartsell@gmail.com");
		adminFacade.createCompany(company);
		Company DbCompany = new Company();
		DbCompany = companyRepo.findCompanyByCompanyName(company.getCompanyName());
		Assert.assertEquals(DbCompany, company);

		adminFacade.removeCompany(company);
	}

	@DirtiesContext
	@Test(expected = CompanyIsNotExistExeption.class)
	public void removeCompanyExeption() throws CompanyIsNotExistExeption, InterruptedException {
		Company IsNotExistExeption = new Company();
		adminFacade.removeCompany(IsNotExistExeption);
	}

	@DirtiesContext
	@Test
	public void updateCompany() throws NameAllreadyExistException, InterruptedException {
		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("vagdani", "0000", "vagdani@gmail.com");
		adminFacade.createCompany(company);
		Company DbCompany = new Company();
		DbCompany = companyRepo.findCompanyByCompanyName(company.getCompanyName());
		Assert.assertEquals(DbCompany, company);

		company.setEmail("@oded");
		company.setPassword("1111");
		adminFacade.updateCompany(company);
		Company testCompany = companyRepo.findCompanyById(company.getId());
		Assert.assertEquals(testCompany, company);

	}

	@DirtiesContext
	@Test(expected = CompanyIsNotExistExeption.class)
	public void updateCompanyExeption() throws CompanyIsNotExistExeption, InterruptedException {
		Company IsNotExistExeption = new Company();
		adminFacade.updateCompany(IsNotExistExeption);
	}

	@DirtiesContext
	@Test
	public void getCompany() throws NameAllreadyExistException, InterruptedException {
		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("shelfx", "3333", "shelfx@gmail.com");
		adminFacade.createCompany(company);
		Company testCompany = new Company();
		testCompany = adminFacade.getCompany(company.getId());
		Assert.assertEquals(testCompany, company);

	}

	@DirtiesContext
	@Test(expected = CompanyIsNotExistExeption.class)
	public void getCompanyExeption() throws CompanyIsNotExistExeption, InterruptedException {
		Company IsNotExistExeption = new Company();
		adminFacade.getCompany(IsNotExistExeption.getId());
	}

	@DirtiesContext
	@Test
	public void getAllCompanies() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Collection<Company> CompaniesList = new ArrayList();
		Company company;
		for (int i = 1; i < 5; i++) {
			company = new Company("inovendi" + i, "4444", "inovendi@gmail.com");
			adminFacade.createCompany(company);
			CompaniesList.add(company);
		}
		Collection<Company> CompaniesList2 = new ArrayList();
		CompaniesList2 = adminFacade.getAllCompanies();
		Assert.assertEquals(CompaniesList2, CompaniesList);
	}

	@DirtiesContext
	@Test
	public void createCustomer() {
		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Customer customer1 = new Customer("oded", "6129453");
		adminFacade.createCustomer(customer1);
		Customer tempCustomerFromDb = customerRepo.findCustomerByCustomerName(customer1.getCustomerName());
		Assert.assertEquals(tempCustomerFromDb, customer1);
	}

	@DirtiesContext
	@Test(expected = NameAllreadyExistException.class)
	public void createCustomerExeption1() {
		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Customer customer1 = new Customer("oded", "6129453");
		adminFacade.createCustomer(customer1);
		Customer tempCustomerFromDb = customerRepo.findCustomerByCustomerName(customer1.getCustomerName());

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Customer customer2 = new Customer("oded", "6129453");
		adminFacade.createCustomer(customer2);

	}

	@DirtiesContext
	@Test
	public void updateCustomer() {
		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Customer customer = new Customer("oded", "6129453");
		adminFacade.createCustomer(customer);
		Customer tempCustomerFromDb = customerRepo.findCustomerByCustomerName(customer.getCustomerName());
		Assert.assertEquals(tempCustomerFromDb, customer);

		Coupon c;
		Collection<Coupon> tempListOfCoupons = new ArrayList();
		for (int i = 0; i < 5; i++) {
			c = new Coupon();
			c.setTitle("testCoupon" + i);
			tempListOfCoupons.add(c);
			couponRepo.save(c);
		}
		customer.setPassword("11111");
		customer.setCoupons(tempListOfCoupons);
		customer.setCustomerName("SSS");

		adminFacade.updateCustomer(customer);

		Customer tempCustomerFromDb2 = customerRepo.findCustomerByCustomerName(customer.getCustomerName());
		Assert.assertEquals(tempCustomerFromDb, customer);

	}

	@DirtiesContext
	@Test(expected = CustomerIsNotExistExeption.class)
	public void updateCustomerExeption() {
		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Customer customer1 = new Customer("oded", "6129453");

		adminFacade.updateCustomer(customer1);
	}

	@DirtiesContext
	@Test
	public void removeCustomer() {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Customer customer = new Customer("oded", "6129453");
		adminFacade.createCustomer(customer);
		Customer tempCustomerFromDb = customerRepo.findCustomerByCustomerName(customer.getCustomerName());
		Assert.assertEquals(tempCustomerFromDb, customer);

		adminFacade.removeCustomer(customer);

	}

	@DirtiesContext
	@Test(expected = CustomerIsNotExistExeption.class)
	public void removeCustomerExeption() {
		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Customer customer1 = new Customer("oded", "6129453");

		adminFacade.removeCustomer(customer1);

	}

	@DirtiesContext
	@Test(expected = CustomerIsNotExistExeption.class)
	public void getCustomerExeption() {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Customer customer1 = new Customer("oded", "6129453");

		adminFacade.getCustomer(customer1.getId());
	}

	@DirtiesContext
	@Test
	public void getCustomer() {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Customer customer = new Customer("oded", "6129453");
		adminFacade.createCustomer(customer);
		Customer tempCustomerFromDb = adminFacade.getCustomer(customer.getId());
		Assert.assertEquals(tempCustomerFromDb, customer);
	}

	@DirtiesContext
	@Test
	public void getAllCustomer() {
		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Customer c;
		Collection<Customer> tempListOfCustomers = new ArrayList();

		for (int i = 0; i < 5; i++) {
			c = new Customer("oded" + i, "1" + i);
			adminFacade.createCustomer(c);
			tempListOfCustomers.add(c);
		}
		Assert.assertEquals(tempListOfCustomers, adminFacade.getAllCustomers());

	}

	// -----------------------------CompanyFacade
	// tests---------------------------

	@DirtiesContext
	@Test
	public void loginCompanyFacade() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
	}

	@DirtiesContext
	@Test
	public void creatCoupon() throws ParseException, NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));

		Coupon newCoupon = new Coupon("shoes", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");

		companyFacade.createCoupon(newCoupon);
		Coupon testCoupon = new Coupon();
		testCoupon = couponRepo.findCouponByTitle(newCoupon.getTitle());

		Assert.assertEquals(testCoupon, newCoupon);

	}

	@DirtiesContext
	@Test(expected = CouponAllReadyExistException.class)
	public void createCouponExeption1() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));

		Coupon newCoupon = new Coupon("shoes", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");
		companyFacade.createCoupon(newCoupon);

		companyFacade.createCoupon(newCoupon);

	}

	@DirtiesContext
	@Test(expected = CouponTitleAllreadyExistException.class)
	public void createCouponExeption2() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));

		Coupon newCoupon = new Coupon("shoes",getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");
		companyFacade.createCoupon(newCoupon);

		Coupon newCoupon2 = new Coupon("shoes", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 500, "no massage");

		companyFacade.createCoupon(newCoupon2);

	}

	@DirtiesContext
	@Test
	public void removeCoupon() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));

		Coupon newCoupon = new Coupon("shoes",getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");

		companyFacade.createCoupon(newCoupon);
		Coupon testCoupon = new Coupon();
		testCoupon = couponRepo.findCouponByTitle(newCoupon.getTitle());

		Assert.assertEquals(testCoupon, newCoupon);

		companyFacade.removeCoupon(newCoupon);

	}

	@DirtiesContext
	@Test(expected = CouponIsNotExistExeption.class)
	public void removeCouponException() throws Exception, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));

		Coupon newCoupon = new Coupon("shoes", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");

		companyFacade.removeCoupon(newCoupon);

	}

	@DirtiesContext
	@Test
	public void updateCoupon() throws NameAllreadyExistException, InterruptedException {
		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));

		Coupon newCoupon = new Coupon("shoes", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");

		companyFacade.createCoupon(newCoupon);
		Coupon testCoupon = new Coupon();
		testCoupon = couponRepo.findCouponByTitle(newCoupon.getTitle());

		Assert.assertEquals(testCoupon, newCoupon);

		newCoupon.setImage("buifs");
		newCoupon.setStartDate(getDateByString("2018-03-11 22:33:10"));
		newCoupon.setTitle("trapes");
		newCoupon.setPrice(400);

		companyFacade.updateCoupon(newCoupon);

		Coupon testCoupon2 = couponRepo.findCouponById(newCoupon.getId());
		Assert.assertEquals(testCoupon2, newCoupon);
	}

	@DirtiesContext
	@Test(expected = CouponIsNotExistExeption.class)
	public void updateCouponException() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));

		Coupon newCoupon = new Coupon("shoes", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");

		newCoupon.setImage("buifs");
		newCoupon.setStartDate(getDateByString("2018-03-11 22:33:10"));
		newCoupon.setTitle("trapes");
		newCoupon.setPrice(400);

		companyFacade.updateCoupon(newCoupon);

	}

	@DirtiesContext
	@Test
	public void getCompany2() throws NameAllreadyExistException, InterruptedException {
		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));

		Company company2 = new Company("adidas", "2222", "adidas@gmail.com");
		adminFacade.createCompany(company2);

		Assert.assertEquals(companyFacade.getCompany(company2.getId()), company2);

	}

	@DirtiesContext
	@Test(expected = CompanyIsNotExistExeption.class)
	public void getCompanyException() throws NameAllreadyExistException, InterruptedException {
		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));

		Company company2 = new Company("adidas", "2222", "adidas@gmail.com");

		companyFacade.getCompany(company2.getId());

	}

	@DirtiesContext
	@Test
	public void getAllCoupons() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Collection<Coupon> tempListOfCoupons = new ArrayList();
		Coupon c;

		for (int i = 0; i < 5; i++) {

			c = new Coupon("shoes" + i, getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
					CouponType.Sport, "elegant", 1000, "no massage");
			tempListOfCoupons.add(c);
			companyFacade.createCoupon(c);

		}
		Assert.assertEquals(tempListOfCoupons, companyFacade.getAllCoupons());

	}

	@DirtiesContext
	@Test(expected = CouponIsNotExistExeption.class)
	public void getAllCouponsException() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));

		companyFacade.getAllCoupons();
	}

	@DirtiesContext
	@Test
	public void getCouponsByType() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Coupon c = new Coupon("shoes",getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");
		Coupon c1 = new Coupon("shoes2",getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Electronic, "elegant", 1000, "no massage");
		Coupon c2 = new Coupon("shoes3", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Food, "elegant", 1000, "no massage");
		Coupon c3 = new Coupon("shoes4", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Fun, "elegant", 1000, "no massage");
		Coupon c4 = new Coupon("shoes5", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Fun, "elegant", 1000, "no massage");
		companyFacade.createCoupon(c);
		companyFacade.createCoupon(c1);
		companyFacade.createCoupon(c2);
		companyFacade.createCoupon(c3);
		companyFacade.createCoupon(c4);

		Collection<Coupon> tempListOfCoupon = new ArrayList();
		tempListOfCoupon = companyFacade.getCouponsByType(CouponType.Fun);

		Assert.assertEquals(tempListOfCoupon, companyFacade.getCouponsByType(CouponType.Fun));

	}

	@DirtiesContext
	@Test(expected = CouponIsNotExistExeption.class)
	public void getCouponsByTypeException() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Coupon c = new Coupon("shoes", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");
		Coupon c1 = new Coupon("shoes2",getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Electronic, "elegant", 1000, "no massage");
		Coupon c2 = new Coupon("shoes3",getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Food, "elegant", 1000, "no massage");
		Coupon c3 = new Coupon("shoes4",getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Fun, "elegant", 1000, "no massage");
		Coupon c4 = new Coupon("shoes5", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Fun, "elegant", 1000, "no massage");
		companyFacade.createCoupon(c);
		companyFacade.createCoupon(c1);
		companyFacade.createCoupon(c2);
		companyFacade.createCoupon(c3);
		companyFacade.createCoupon(c4);

		System.out.println(companyFacade.getCouponsByType(CouponType.Movies));

	}

	@DirtiesContext
	@Test
	public void getCouponsByprice() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company1 = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Coupon c;
		Collection<Coupon> ListOfCouponsByPrice = new ArrayList();
		for (int i = 0; i < 3; i++) {

			c = new Coupon("shoes" + i, getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
					CouponType.Sport, "elegant", 1000 * i, "no massage");

			companyFacade.createCoupon(c);
			ListOfCouponsByPrice.add(c);
		}

		Assert.assertEquals(ListOfCouponsByPrice, companyFacade.getCouponsByPrice(3001));

	}

	@DirtiesContext
	@Test(expected = PriceCantBeMinusException.class)
	public void getCouponsBypriceException1() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company1 = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Coupon c;

		for (int i = 0; i < 3; i++) {

			c = new Coupon("shoes" + i,getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
					CouponType.Sport, "elegant", 1000 * i, "no massage");

			companyFacade.createCoupon(c);

		}

		companyFacade.getCouponsByPrice(-10);

	}

	@DirtiesContext
	@Test(expected = CouponIsNotExistExeption.class)
	public void getCouponsBypriceException2() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Coupon c;

		for (int i = 3; i < 6; i++) {

			c = new Coupon("shoes" + i, getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
					CouponType.Sport, "elegant", 1000 * i, "no massage");

			companyFacade.createCoupon(c);
		}
		companyFacade.getCouponsByPrice(100);

	}

	@DirtiesContext
	@Test
	public void getCouponsByDate() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company1 = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Collection<Coupon> couponListByNow = new ArrayList();

		Coupon c = new Coupon("shoes", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");
		Coupon c1 = new Coupon("shoes2", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");
		Coupon c2 = new Coupon("shoes3", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");
		Coupon c3 = new Coupon("shoes4", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");
		Coupon c4 = new Coupon("shoes5", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");

		companyFacade.createCoupon(c);
		companyFacade.createCoupon(c1);
		companyFacade.createCoupon(c2);
		companyFacade.createCoupon(c3);
		companyFacade.createCoupon(c4);

		couponListByNow.add(c);
		couponListByNow.add(c1);
		couponListByNow.add(c2);
		couponListByNow.add(c3);
		couponListByNow.add(c4);

		Assert.assertEquals(couponListByNow, companyFacade.getCouponsByDate());

	}

	@DirtiesContext
	@Test(expected = CouponIsNotAvailableExeption.class)
	public void getCouponsByDateException() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Company company1 = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Coupon c = new Coupon("shoes", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");
		Coupon c1 = new Coupon("shoes2", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");
		Coupon c2 = new Coupon("shoes3", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");
		Coupon c3 = new Coupon("shoes4", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");
		Coupon c4 = new Coupon("shoes5", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");

		companyFacade.createCoupon(c);
		companyFacade.createCoupon(c1);
		companyFacade.createCoupon(c2);
		companyFacade.createCoupon(c3);
		companyFacade.createCoupon(c4);

		companyFacade.getCouponsByDate();

	}

	//Customer Facade tests
	//

	@DirtiesContext
	@Test
	public void loginCustomerFacade() {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);
		Customer customer = new Customer("oded", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("oded", "1234", ClientType.Customer));
	}

	@DirtiesContext
	@Test
	public void purchesCoupon() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);

		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Customer customer = new Customer("oded", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("oded", "1234", ClientType.Customer));

		customerFacade.login("oded", "1234", ClientType.Customer);

		Coupon c = new Coupon("shoes", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");

		companyFacade.createCoupon(c);

		customerFacade.purchaseCoupon(c);
		
		Assert.assertTrue(customerFacade.getAllPurchaseCoupons().contains(c));		
}

	@DirtiesContext
	@Test(expected = CouponIsNotAvailableExeption.class)
	public void purchesCouponException1() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);

		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Customer customer = new Customer("oded", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("oded", "1234", ClientType.Customer));

		customerFacade.login("oded", "1234", ClientType.Customer);

		Coupon c = new Coupon("shoes", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
				CouponType.Sport, "elegant", 1000, "no massage");

		companyFacade.createCoupon(c);
		customerFacade.purchaseCoupon(c);

	}

	
	
	@DirtiesContext
	@Test (expected = CouponIsNotExistExeption.class)
	public void purchesCouponException2() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);

		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Customer customer = new Customer("oded", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("oded", "1234", ClientType.Customer));

		customerFacade.login("oded", "1234", ClientType.Customer);

		Coupon c = new Coupon("shoes", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), -1,
				CouponType.Sport, "elegant", 1000, "no massage");

		companyFacade.createCoupon(c);
		customerFacade.purchaseCoupon(c);
	}
	
	@DirtiesContext
	@Test (expected = CouponIsNotExistExeption.class)
	public void purchesCouponException3() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);

		Company company = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Customer customer = new Customer("oded", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("oded", "1234", ClientType.Customer));

		customerFacade.login("oded", "1234", ClientType.Customer);

		Coupon c = new Coupon("shoes", getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 100,
				CouponType.Sport, "elegant", 1000, "no massage");

		
		customerFacade.purchaseCoupon(c);
	}
	
	@DirtiesContext
	@Test
	public void getAllPurchesCoupon() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);

		Company company1 = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Customer customer = new Customer("oded", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("oded", "1234", ClientType.Customer));

		customerFacade.login("oded", "1234", ClientType.Customer);

		Collection <Coupon> listOfTempCoupons = new ArrayList();
		Coupon c;
		for(int i = 0 ; i < 5 ; i ++){
			
			c = new Coupon("shoes"+i, getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
					CouponType.Sport, "elegant", 1000, "no massage");
			
			companyFacade.createCoupon(c);
			listOfTempCoupons.add(c);
			customerFacade.purchaseCoupon(c);
		}
		
		Assert.assertEquals(listOfTempCoupons, customerFacade.getAllPurchaseCoupons());
		
	}
	
	@DirtiesContext
	@Test
	public void getAllPurchesCouponByType() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);

		Company company1 = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Customer customer = new Customer("oded", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("oded", "1234", ClientType.Customer));

		customerFacade.login("oded", "1234", ClientType.Customer);

		Collection <Coupon> listOfTempCoupons = new ArrayList();
		Coupon c;
		for(int i = 0 ; i < 5 ; i ++){
			
			c = new Coupon("shoes"+i, getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
					CouponType.Sport, "elegant", 1000, "no massage");
			
			companyFacade.createCoupon(c);
			listOfTempCoupons.add(c);
			customerFacade.purchaseCoupon(c);
		}
		
		Assert.assertEquals(listOfTempCoupons, customerFacade.getAllPurchaseCouponsByType(CouponType.Sport));
		
	}
	
	
	@DirtiesContext
	@Test
	public void getAllPurchesCouponByPrice() throws NameAllreadyExistException, InterruptedException {

		this.adminFacade.login("oded", "6129453", ClientType.Admin);

		Company company1 = new Company("nike", "1111", "nike@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("nike", "1111", ClientType.Company));
		companyFacade.login("nike", "1111", ClientType.Company);

		Customer customer = new Customer("oded", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("oded", "1234", ClientType.Customer));

		customerFacade.login("oded", "1234", ClientType.Customer);

		Collection <Coupon> listOfTempCoupons = new ArrayList();
		Coupon c;
		for(int i = 0 ; i < 5 ; i ++){
			
			c = new Coupon("shoes"+i, getDateByString("2018-03-11 22:33:10"), getDateByString("2018-03-11 22:33:10"), 10,
					CouponType.Sport, "elegant", 1000, "no massage");
			
			companyFacade.createCoupon(c);
			listOfTempCoupons.add(c);
			customerFacade.purchaseCoupon(c);
		}
		
		Assert.assertEquals(listOfTempCoupons, customerFacade.getAllPurchaseCouponsByPrice(1000));
		
	}
	
	
	/**
	 * 
	 * @param str dd-M-yyyy hh:mm:ss
	 * @return Date
	 * @throws ParseException 
	 */
	private Date getDateByString(String str) {
		try {
			return new SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}