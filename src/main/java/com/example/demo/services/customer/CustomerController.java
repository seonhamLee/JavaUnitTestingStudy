package com.example.demo.services.customer;


import com.example.demo.services.customer.domain.Customer;
import com.example.demo.services.customer.domain.Store;
import com.example.demo.services.customer.domain.StoreImpl;
import com.example.demo.services.customer.repository.CustomerRepository;
import com.example.demo.services.customer.repository.ProductRepository;
import com.example.demo.services.email_gateway.IEmailGateway;

public class CustomerController {

	private final CustomerRepository customerRepository = new CustomerRepository();

	private final ProductRepository productRepository = new ProductRepository();

	private final Store mainStore = new StoreImpl();

	private final IEmailGateway emailGateway;

	public CustomerController(IEmailGateway emailGateway) {
		this.emailGateway = emailGateway;
	}

	public boolean purchase(int customerId, int productId, int quantity) {
		final Customer customer = customerRepository.getById(customerId);
		final String product = productRepository.getById(productId);

		final boolean isSuccess = customer.purchase(mainStore, product, quantity);

		if (isSuccess) {
			emailGateway.sendReceipt(customer.getEmail(), product, quantity);
		}
		return isSuccess;
	}
}