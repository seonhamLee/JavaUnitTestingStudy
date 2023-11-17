package com.example.demo.chapter2;

import com.example.demo.services.customer.domain.Customer;
import com.example.demo.services.customer.domain.Product;
import com.example.demo.services.customer.domain.Store;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

public class LondonStyleCustomerTest {

	// 런던파 스타일의 테스트

	@Mock
	Store storeMock; // SUT의 협력자를 Mock으로 대체한다.

	@Test
	public void purchase_succeeds_when_enough_inventory() {

		// given
		final Customer customer = new Customer(); // SUT

		given(storeMock.hasEnoughInventory(Product.shampoo, 5)).willReturn(true);
		// SUT를 분기하는 협력자의 메서드의 반환 값을 규정한다.

		// when
		Boolean success = customer.purchase(storeMock, Product.shampoo, 5);
		//

		// then
		Assertions.assertTrue(success);
		then(storeMock)
				.should(times(1))
				.removeInventory(Product.shampoo, 5);
		// 분기 이후 SUT의 협력자 메서드 호출이 행해졌는지 여부를 확인한다.
	}

	@Test
	public void purchase_fails_when_not_enough_inventory() {

		// given
		final Customer customer = new Customer(); // SUT

		given(storeMock.hasEnoughInventory(Product.shampoo, 5)).willReturn(false);
		// SUT를 분기하는 협력자의 메서드의 반환 값을 규정한다.

		// when
		Boolean success = customer.purchase(storeMock, Product.shampoo, 5);

		// then
		Assertions.assertFalse(success);
		then(storeMock).should(never()).removeInventory(Product.shampoo, 5);
		// 분기 이후 SUT의 협력자 메서드 호출이 행해졌는지 여부를 확인한다.
	}
}
