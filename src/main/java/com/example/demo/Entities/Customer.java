package com.example.demo.Entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.stereotype.Component;
/**
 * customer entity
 * @author oded
 *
 */
@Component
@Entity(name = "CUSTOMERS")
public class Customer {

	@Id
	@GeneratedValue
	private int id;

	@Column
	private String customerName;

	@Column
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.REFRESH })
	@JoinTable(name = "Customer_Coupon", joinColumns = @JoinColumn(name = "Customer_id"), inverseJoinColumns = @JoinColumn(name = "Coupon_id"))

	private Collection<Coupon> coupons;
    /**
     * empty ctor
     */
	public Customer() {
		super();
	}
/**
 * get the customer id
 * @return id
 */
	public int getId() {
		return id;
	}
/**
 * get list of coupons
 * @return coupons
 */
	public Collection<Coupon> getCoupons() {
		return coupons;
	}
/**
 * set coupon list
 * @param coupons
 */
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
/**
 * get customer name
 * @return customer
 */
	public String getCustomerName() {
		return customerName;
	}
/**
 * set customer by name
 * @param customerName
 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
/**
 * get customer password
 * @return password
 */
	public String getPassword() {
		return password;
	}
/**
 * ability to set the password
 * @param password
 */
	public void setPassword(String password) {
		this.password = password;
	}
/**
 * ctor
 * @param customer
 * @param password
 */
	public Customer(String customer, String password) {
		super();

		this.customerName = customer;
		this.password = password;
	}
/**
 * print the fields informetion of customer 
 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", customer=" + customerName + ", password=" + password + "]";
	}


/**
 * boolean method that override object method and ask if the input object instance of customer object
 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Customer)) {
			return false;
		}

		return id == ((Customer) obj).getId();
	}

}