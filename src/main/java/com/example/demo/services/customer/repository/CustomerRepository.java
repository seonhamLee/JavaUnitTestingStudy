package com.example.demo.services.customer.repository;

import com.example.demo.services.customer.domain.Customer;

public class CustomerRepository {

	public Customer getById(int customerId) {
		return new Customer("dummy email");
	}
}