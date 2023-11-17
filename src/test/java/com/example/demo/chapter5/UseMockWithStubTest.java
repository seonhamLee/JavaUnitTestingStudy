package com.example.demo.chapter5;

import com.example.demo.services.customer.domain.Customer;
import com.example.demo.services.customer.domain.Product;
import com.example.demo.services.customer.domain.Store;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

public class UseMockWithStubTest {

	@Mock
	Store storeMock; // 목이면서 스텁인 의존성

	@Test
	public void purchase_fails_when_not_enough_inventory() {

		// given
		final Customer sut = new Customer(); // SUT

		given(storeMock.hasEnoughInventory(Product.shampoo, 5)).willReturn(false);
		// sut로 인한 사이드 이펙트(밖으로 나가는 의존성)이 초래된다. (목)

		// when
		Boolean success = sut.purchase(storeMock, Product.shampoo, 5);
		// sut 메서드의 입력 데이터를 가져다준다.

		// then
		Assertions.assertFalse(success);
		then(storeMock).should(never()).removeInventory(Product.shampoo, 5);
		// 분기 이후 SUT의 협력자 메서드 호출이 행해졌는지 여부를 확인한다.
	}
}
