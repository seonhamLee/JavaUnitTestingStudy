package com.example.demo.chapter3.calculator.anti_pattern;

import com.example.demo.services.customer.domain.Product;
import com.example.demo.services.customer_anti_pattern.domain.Customer_AntiPattern;
import com.example.demo.services.customer_anti_pattern.domain.StoreImpl_AntiPattern;
import com.example.demo.services.customer_anti_pattern.domain.Store_AntiPattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// 동작 단위여야 할 고전파 테스트의 안티 패턴 사례를 다룬다,
public class DoubleActTest {

	@Test
	public void purchase_succeeds_when_enough_inventory() {

		// arrange(= given)
		final Customer_AntiPattern customer = new Customer_AntiPattern(); // SUT

		Store_AntiPattern store = new StoreImpl_AntiPattern();
		store.addInventory(Product.shampoo, 10);

		// act(= when)
		Boolean success = customer.purchase(store, Product.shampoo, 5);
		store.removeInventory(success, Product.shampoo, 5);
		// 실행 구절이 두 줄이며, 이는 안티 패턴이다.

		// assert(= then)
		Assertions.assertTrue(success);
		Assertions.assertEquals(
				store.getInventory(Product.shampoo),
				5
		);
	}
}
