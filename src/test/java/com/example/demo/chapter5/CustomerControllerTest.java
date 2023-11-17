package com.example.demo.chapter5;

import com.example.demo.services.customer.CustomerController;
import com.example.demo.services.customer.domain.Product;
import com.example.demo.services.customer.domain.Store;
import com.example.demo.services.email_gateway.IEmailGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@ExtendWith(MockitoExtension.class) // 행동 규정이 안된 mock이 null 포인터가 되지 않게 도와준다.
public class CustomerControllerTest {

	@Mock
	IEmailGateway mock;

	@Mock
	Store storeMock;

	@Test
	public void successful_purchase() {

		CustomerController sut = new CustomerController(mock);

		Boolean isSuccess = sut.purchase(1, 2, 5);

		Assertions.assertTrue(isSuccess);
		then(mock).should(only()).sendReceipt("dummy email", Product.shampoo, 5);
		// 사이드 이펙트가 발생하는 의존성에 대해 적절히 목을 사용하였다.
	}

	// 허나 아래 테스트에서 검증하는 것은 CustomerController 어플리케이션(시스템) 내부의 통신이므로, 구현 세부 사항에 해당한다.

//	@Test
//	public void purchase_succeeds_when_enough_inventory() {
//
//		given(storeMock.hasEnoughInventory(Product.shampoo, 5)).willReturn(true);
//		Customer customer = new Customer("dummy email");
//
//		Boolean isSuccess = customer.purchase(storeMock, Product.shampoo, 5);
//
//		Assertions.assertTrue(isSuccess);
//		then(storeMock).should(only()).removeInventory(Product.shampoo, 5);
//	}
}
