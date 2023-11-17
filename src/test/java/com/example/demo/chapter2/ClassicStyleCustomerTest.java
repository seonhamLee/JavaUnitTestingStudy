package com.example.demo.chapter2;


import com.example.demo.services.customer.domain.Customer;
import com.example.demo.services.customer.domain.Product;
import com.example.demo.services.customer.domain.Store;
import com.example.demo.services.customer.domain.StoreImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClassicStyleCustomerTest {

	// 고전파 스타일의 테스트

	@Test
	public void purchase_succeeds_when_enough_inventory() {

		// given
		final Customer customer = new Customer(); // SUT

		Store store = new StoreImpl(); // SUT의 협력자를 대체하지 않고, 운영용 인스턴스를 사용한다.
		store.addInventory(Product.shampoo, 10);

		// when
		Boolean success = customer.purchase(store, Product.shampoo, 5);

		// then
		Assertions.assertTrue(success);
		Assertions.assertEquals(
				store.getInventory(Product.shampoo),
				5
		); // 호출 이후 협력자(Store)의 상태를 검증한다.
	}

	@Test
	public void purchase_fails_when_not_enough_inventory() {

		// given
		final Customer customer = new Customer(); // SUT

		Store store = new StoreImpl(); // SUT의 협력자
		store.addInventory(Product.shampoo, 10);

		// when
		Boolean success = customer.purchase(store, Product.shampoo, 15);

		// then
		Assertions.assertFalse(success);
		Assertions.assertEquals(
				store.getInventory(Product.shampoo),
				10
		);
	}
}