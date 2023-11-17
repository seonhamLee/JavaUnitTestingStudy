package com.example.demo.chapter6;

import com.example.demo.services.order_product.PriceEngine;
import com.example.demo.services.order_product.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OutputBasedTest {

	// 출력 기반 테스트의 사례.

	@Test
	void Discount_of_two_products() throws Exception {
		// Given
		final Product product1 = new Product("Hand wash");
		final Product product2 = new Product("Shampoo");
		final PriceEngine sut = new PriceEngine();

		// When
		final double discount = sut.calculateDiscount(product1, product2);

		// Then
		assertThat(discount).isEqualTo(0.02d);
	}
}
