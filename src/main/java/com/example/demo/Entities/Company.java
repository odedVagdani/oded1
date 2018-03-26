package com.example.demo.Entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Component;
/**
 * company entity
 * @author oded
 *
 */
@Component
@Entity(name = "COMPANIES")
public class Company {

	@Id
	@GeneratedValue
	private int id;

	@Column
	private String companyName;

	@Column
	private String password;

	@Column
	private String email;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "Company_id")
	private Collection<Coupon> coupons;

	
    /**
     * toString method that print the entity fields
     */
	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", password=" + password + ", email=" + email
				+ "]";
	}
    /**
     * boolean method that override object method and ask if the input object instance of company object
     */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Company)) {
			return false;
		}

		return id == ((Company) obj).getId();
	    }
/**
 * id value
 * @return id
 */
	public int getId() {
		return id;
	}
/**
 * list of coupons
 * @return coupons from DB
 */
	public Collection<Coupon> getCoupons() {
		return coupons;
	}
/**
 * method that show list of coupons and ability to update 
 * @param coupons
 */
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
/**
 * method that show to the user comapnies from DB by name
 * @return companies from DB
 */
	public String getCompanyName() {
		return companyName;
	}
/**
 * method that show list of companies by name and ability to update 
 * @param companyName
 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
/**
 * method that give to the user the password to login 
 * @return company password 
 */
	public String getPassword() {
		return password;
	}
/**
 * method that give the ability to chnage the password to login
 * @param password
 */
	public void setPassword(String password) {
		this.password = password;
	}
/**
 * show the company e-mail
 * @return e mail
 */
	public String getEmail() {
		return email;
	}
/**
 * method to change the company e mail
 * @param email
 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 *empty ctor
	 */
	public Company() {
		super();
	}
	/**
	 * ctor
	 * @param companyName
	 * @param password
	 * @param email
	 * @param coupons
	 */
	public Company(String companyName, String password, String email, Collection<Coupon> coupons) {
		super();
		this.companyName = companyName;
		this.password = password;
		this.email = email;
		this.coupons = coupons;
	}

/**
 * ctor
 * @param companyName
 * @param password
 * @param email
 */
	public Company(String companyName, String password, String email) {
		super();
		this.companyName = companyName;
		this.password = password;
		this.email = email;
	}

}