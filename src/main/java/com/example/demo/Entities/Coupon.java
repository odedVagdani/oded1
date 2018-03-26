package com.example.demo.Entities;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.io.Serializable;

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
     * coupon entity
     * @author oded
     *
     */
    @Component
	@Entity(name="COUPONS")
	public class Coupon {
		
		

		@Id @GeneratedValue
		private int id;
		
		@Column
		private String title;

		@Column
		private LocalDate startDate;
		
		@Column
		private LocalDate endDate;
		
		@Column
		private int amount;
		
		@Column
		private CouponType type;
		
		@Column
		private String message;
		
		@Column
		private double price;
		
		@Column
		private String image;
		
		@ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH ,  CascadeType.REFRESH})
		@JoinTable(name="Customer_Coupon",
					joinColumns=@JoinColumn(name="Coupon_id"),
					inverseJoinColumns = @JoinColumn(name = "Customer_id"))
		
		private Collection <Customer> customers ;
/**
 * get the id
 * @return id
 */
		public int getId() {
			return id;
		}
/**
 * et the title
 * @return title
 */ 
		public String getTitle() {
			return title;
		}
/**
 * ability to set the title
 * @param title
 */
		public void setTitle(String title) {
			this.title = title;
		}
/**
 * get the coupon start date
 * @return date
 */
		public LocalDate getStartDate() {
			return startDate;
		}
/**
 * ability to update the start date
 * @param startDate
 */
		public void setStartDate(LocalDate startDate) {
			this.startDate = startDate;
		}
/**
 * get the coupon end date
 * @return date
 */
		public LocalDate getEndDate() {
			return endDate;
		}
/**
 * ability to update the end date
 * @param endDate
 */
		public void setEndDate(LocalDate endDate) {
			this.endDate = endDate;
		}
/**
 * get the amount
 * @return amount
 */
		public int getAmount() {
			return amount;
		}
/**
 * ability to set the amount
 * @param amount
 */
		public void setAmount(int amount) {
			this.amount = amount;
		}
/**
 * get the coupon type
 * @return type
 */
		public CouponType getType() {
			return type;
		}
/**
 * ability to change the type
 * @param type
 */
		public void setType(CouponType type) {
			this.type = type;
		}
/**
 * get the message
 * @return message
 */
		public String getMessage() {
			return message;
		}
/**
 * ability to set the message
 * @param message
 */
		public void setMessage(String message) {
			this.message = message;
		}
/**
 * get the coupon price
 * @return price
 */
		public double getPrice() {
			return price;
		}
/**
 * set the coupon price
 * @param price
 */
		public void setPrice(double price) {
			this.price = price;
		}
/**
 * get the coupon image
 * @return image
 */
		public String getImage() {
			return image;
		}
/**
 * ability to set the coupon image
 * @param image
 */
		public void setImage(String image) {
			this.image = image;
		}
/**
 * ctor
 * @param title
 * @param startDate
 * @param endDate
 * @param amount
 * @param type
 * @param message
 * @param price
 * @param image
 */
		public Coupon( String title, LocalDate startDate, LocalDate endDate, int amount, CouponType type, String message,
				double price, String image) {
			super();
			
			this.title = title;
			this.startDate = startDate;
			this.endDate = endDate;
			this.amount = amount;
			this.type = type;
			this.message = message;
			this.price = price;
			this.image = image;
		}
/**
 * empty ctor
 */
		public Coupon() {
			super();
		}
/**
 * print the coupon fields in string
 */
		@Override
		public String toString() {
			return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
					+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
					+ image + "]";
		}
		/**
		 * boolean method that override object method and ask if the input object instance of coupon object
		 */
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Coupon)) {
				return false;
			}

			return id == ((Coupon) obj).getId()
					;
		}
	}