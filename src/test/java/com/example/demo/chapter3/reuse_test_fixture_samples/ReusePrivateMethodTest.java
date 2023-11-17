package com.example.demo.chapter3.reuse_test_fixture_samples;

import com.example.demo.services.customer.domain.Customer;
import com.example.demo.services.customer.domain.Product;
import com.example.demo.services.customer.domain.Store;
import com.example.demo.services.customer.domain.StoreImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// 비공개 팩토리 메서드를 활용한 테스트 픽스쳐 재사용
public class ReusePrivateMethodTest {

	@Test
	public void purchase_succeeds_when_enough_inventory() {

		// given
		final Customer customer = createCustomer(); // SUT
		Store store = createStoreWithInventory(Product.shampoo, 10);

		// 코드가 간결해지고 가독성도 좋으면서 결합도도 높지 않다.

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
		final Customer customer = createCustomer(); // SUT

		Store store = createStoreWithInventory(Product.shampoo, 10);

		// when
		Boolean success = customer.purchase(store, Product.shampoo, 15);

		// then
		Assertions.assertFalse(success);
		Assertions.assertEquals(
				store.getInventory(Product.shampoo),
				10
		);
	}


	// 아래 비공개 메서드를 활용하는 것을 권장한다.
	private Customer createCustomer() {

		return new Customer();
	}

	private Store createStoreWithInventory(String product, Integer quantity) {

		Store store = new StoreImpl();

		store.addInventory(product, quantity);

		return store;
	}
}
