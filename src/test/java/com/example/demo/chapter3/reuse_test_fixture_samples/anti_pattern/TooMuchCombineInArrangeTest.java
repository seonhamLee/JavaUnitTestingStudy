package com.example.demo.chapter3.reuse_test_fixture_samples.anti_pattern;

import com.example.demo.services.customer.domain.Customer;
import com.example.demo.services.customer.domain.Product;
import com.example.demo.services.customer.domain.Store;
import com.example.demo.services.customer.domain.StoreImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TooMuchCombineInArrangeTest {

	private final Customer sut; // SUT 구분을 위해 인스턴스명을 sut로 하는 것을 권장한다.

	private final Store store;

	// JUnit에선 클래스 내 각 테스트마다, 테스트 실행 전 아래 생성자를 호출한다.
	public TooMuchCombineInArrangeTest() {

		// 지나치게 높은 결합도의 생성자.

		this.sut = new Customer();

		this.store = new StoreImpl();
		store.addInventory(Product.shampoo, 10);

		// quantity 를 10에서 15로 바꾼다면, 아래 테스트들을 실패하게 된다.

		// 해당 코드를 수정하여도 테스트 코드에 영향이 없는 선에서만 결합하는 것을 권장한다.
	}


	@Test
	public void purchase_succeeds_when_enough_inventory() {

		// given

		// when
		Boolean success = sut.purchase(store, Product.shampoo, 5);

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

		// when
		Boolean success = sut.purchase(store, Product.shampoo, 15);

		// then
		Assertions.assertFalse(success);
		Assertions.assertEquals(
				store.getInventory(Product.shampoo),
				10
		);
	}
}
