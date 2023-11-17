package com.example.demo.chapter3.reuse_test_fixture_samples.anti_pattern;

import java.time.LocalDateTime;

import com.example.demo.samples.reuse_test_fixture_sample.Delivery;
import com.example.demo.samples.reuse_test_fixture_sample.DeliveryService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

// 테스트 명명 규칙을 지나치게 따른 경우를 다룬다.
public class TooMuchLongNameTest {

	@Test
	public void IsDeleveryValid_InvalidDate_ReturnsFalse() {
		// [테스트 대상 메서드]_[시나리오]_[예상 결과]의 규칙을 준수했으나, 가독성이 떨어진다.

		// Given
		final DeliveryService sut = new DeliveryService();
		final LocalDateTime pastDate = LocalDateTime.now().minusDays(1L);
		final Delivery delivery = new Delivery(pastDate);

		// When
		final boolean isValid = sut.isDeliveryValid(delivery);

		// Then
		assertFalse(isValid);
	}

	@Test
	public void Delivery_with_a_past_date_is_invalid() {
		// 해당 테스트 명은 동작에 관해 간단명료하게 설명한다. 이와 같은 명명이 더 가독성이 좋다.

		// Given
		final DeliveryService sut = new DeliveryService();
		final LocalDateTime pastDate = LocalDateTime.now().minusDays(1L);
		final Delivery delivery = new Delivery(pastDate);

		// When
		final boolean isValid = sut.isDeliveryValid(delivery);

		// Then
		assertFalse(isValid);
	}
}
