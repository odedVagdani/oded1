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
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.EnumFacade;
import com.example.demo.Exception.CompanyIsNotExistExeption;
import com.example.demo.Exception.CustomerAllReadyExistException;
import com.example.demo.Exception.CustomerIsNotExistExeption;
import com.example.demo.Exception.NameAllreadyExistException;
import com.example.demo.Exception.PasswordNotCorrectException;
import com.example.demo.Exception.UserNameNotMatchException;
/**
 * class of the system manager
 * @author oded
 *
 */
@Component
public class AdminFacade implements CouponClientFacade {

	@Autowired
	private CompanyDBDAO companyDbdao;
	@Autowired
	private CustomerDBDAO customerDbdao;
	@Autowired
	private CouponDBDAO couponDbdao;
	@Autowired
	private TransactionsDBDAO transactionsDbdao;

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		if (name != ("oded")) {
			transactionsDbdao.writeToTable("login", false, EnumFacade.AdminFacade);
			throw new UserNameNotMatchException("The user name is not correct");
		}
		if (password != "6129453") {
			transactionsDbdao.writeToTable("login", false, EnumFacade.AdminFacade);
			throw new PasswordNotCorrectException("Worng password");
		}
     	if (name.equals("oded") && password.equals("6129453") && clientType.equals(clientType.Admin)) {
			transactionsDbdao.writeToTable("login", true, EnumFacade.AdminFacade);
			return this;
		}
		return null;
	}

	public AdminFacade() {

	}

	/**
	 * this method check if company exist and give you option to create a new comopany
	 * @param company
	 * @throws NameAllreadyExistException
	 * @throws InterruptedException
	 */
	public void createCompany(Company company) throws NameAllreadyExistException, InterruptedException {

		if (companyDbdao.getCompanyByName(company.getCompanyName()) != null) {
			transactionsDbdao.writeToTableCompany("createCompany", false, EnumFacade.AdminFacade, company.toString());
			throw new NameAllreadyExistException("This company name is exist");
		}

		companyDbdao.createCompany(company);
		transactionsDbdao.writeToTableCompany("createCompany", true, EnumFacade.AdminFacade, company.toString());
	}
	/**
	 * this method check if company exist by id and give a option to delete the company
	 * @param company
	 * @throws CompanyIsNotExistExeption
	 * @throws InterruptedException
	 */
	public void removeCompany(Company company) throws CompanyIsNotExistExeption, InterruptedException {

		if (companyDbdao.getCompany(company.getId()) == null) {

			throw new CompanyIsNotExistExeption("company doesnt exist");

		}
		transactionsDbdao.writeToTableCompany("removeCompany", true, EnumFacade.AdminFacade, company.toString());
		companyDbdao.removeCompany(company);
	}
	/**
	 * method that get the company and from the DB and the option to update the company
	 * @param company
	 * @throws CompanyIsNotExistExeption
	 * @throws InterruptedException
	 */
	public void updateCompany(Company company) throws CompanyIsNotExistExeption, InterruptedException {

		if (companyDbdao.getCompany(company.getId()) != null) {
			transactionsDbdao.writeToTableCompany("updateCompany", true, EnumFacade.AdminFacade, company.toString());
			companyDbdao.updateCompany(company);

		} else
			throw new CompanyIsNotExistExeption("company doenst exist");
	}
	/**
	 * method that show you company from the DB by id
	 * @param id
	 * @return
	 * @throws CompanyIsNotExistExeption
	 * @throws InterruptedException
	 */
	public Company getCompany(int id) throws CompanyIsNotExistExeption, InterruptedException {

		if (companyDbdao.getCompany(id) == null) {
			throw new CompanyIsNotExistExeption("company doesnt exist in db");
		}
		transactionsDbdao.writeToTableCompany("getCompany", true, EnumFacade.AdminFacade,
				companyDbdao.getCompany(id).toString());
		return companyDbdao.getCompany(id);
	}
	/**
	 * show to the user list of companies from the DB
	 * @return
	 * @throws InterruptedException
	 */
	public Collection getAllCompanies() throws InterruptedException {
		Collection<Company> AllCompaniesList = new ArrayList<Company>();
		AllCompaniesList = companyDbdao.getAllCompanies();
		transactionsDbdao.writeToTable("getAllCompany", true, EnumFacade.AdminFacade);
		return AllCompaniesList;

	}
	/**
	 * this method check if customer exist and give you option to create a new customer
	 * @param customer
	 * @throws NameAllreadyExistException
	 */
	public void createCustomer(Customer customer) throws NameAllreadyExistException {

		if (customerDbdao.getCustomerByName(customer.getCustomerName()) == null) {
			transactionsDbdao.writeToTableCustomer("createCustomer", true, EnumFacade.AdminFacade, customer.toString());
			customerDbdao.createCustomer(customer);
		}

		else
			throw new NameAllreadyExistException("there is a customer by this name all ready, please chage");
		transactionsDbdao.writeToTable("NameAllreadyExistException", false, EnumFacade.AdminFacade);
	}
	/**
	 * method that get the customer and from the DB and the option to update the customer
	 * @param customer
	 * @throws CustomerIsNotExistExeption
	 */
	public void updateCustomer(Customer customer) throws CustomerIsNotExistExeption {

		if (customerDbdao.getCustomer(customer.getId())== null) {
			transactionsDbdao.writeToTable("CustomerDoesntExistExeption", false, EnumFacade.AdminFacade);
			throw new CustomerIsNotExistExeption("the customer doesnt exist");
		}

		customerDbdao.updateCustomer(customer);
		transactionsDbdao.writeToTableCustomer("updateCustomer", true, EnumFacade.AdminFacade, customer.toString());
	}
	/**
	 * this method check if customer exist by id and give a option to delete the customer
	 * @param customer
	 * @throws CustomerIsNotExistExeption
	 */
	public void removeCustomer(Customer customer) throws CustomerIsNotExistExeption {

		if (customerDbdao.getCustomer(customer.getId()) != null) {
	
			transactionsDbdao.writeToTableCustomer("removeCustomer", true, EnumFacade.AdminFacade, customer.toString());
			customerDbdao.removeCustomer(customer);
		}

		else
			throw new CustomerIsNotExistExeption("the customer doesnt exist");
		transactionsDbdao.writeToTable("CustomerDoesntExistExeption", false, EnumFacade.AdminFacade);

	}
	/**
	 * method that show customer by id
	 * @param id
	 * @return
	 * @throws CustomerIsNotExistExeption
	 */
	public Customer getCustomer(int id) throws CustomerIsNotExistExeption{
		if (customerDbdao.getCustomer(id)!=null){
			transactionsDbdao.writeToTableCustomer("getCustomer", true, EnumFacade.AdminFacade,
					customerDbdao.getCustomer(id).toString());
			return customerDbdao.getCustomer(id);
		}
		
		throw new CustomerIsNotExistExeption("the customer doesnt exist");
	}
	/**
	 * method that present a list of customers from the DB
	 * @return
	 */
	public Collection getAllCustomers() {

		Collection<Customer> listOfCustomers = new ArrayList<Customer>();
		listOfCustomers = customerDbdao.getAllCustomers();
		transactionsDbdao.writeToTable("getAllCustomers", true, EnumFacade.AdminFacade);
		return listOfCustomers;

	}

	

}