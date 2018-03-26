package com.example.demo.Entities;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
/*
 * @Author: oded
 */
public interface TransactionsRepo extends CrudRepository <Transactions,Integer> {
	
	
}