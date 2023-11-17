package com.example.demo.chapter3.reuse_test_fixture_samples;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import com.example.demo.samples.reuse_test_fixture_sample.Delivery;
import com.example.demo.samples.reuse_test_fixture_sample.DeliveryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// 매개변수화된 테스트 사용의 절충안을 보여준다. 부정적, 긍정적 결과에 따라 테스트를 나누었다.
public class RightParameterizedTest {

	@ParameterizedTest
	@MethodSource("provideArguments")
	public void Can_detect_an_invalid_delivery_date(
			int daysFromNow
	) {
		// Given
		final DeliveryService sut = new DeliveryService();
		final LocalDateTime deliveryDate = LocalDateTime.now().plusDays(daysFromNow);
		final Delivery delivery = new Delivery(deliveryDate);

		// When
		final boolean isValid = sut.isDeliveryValid(delivery);

		// Then
		assertFalse(isValid); // 긍정적인 시나리오
	}

	@Test
	public void The_soonest_delivery_date_is_two_days_from_now() {
		// Given
		final DeliveryService sut = new DeliveryService();
		final LocalDateTime deliveryDate = LocalDateTime.now().plusDays(2L);
		final Delivery delivery = new Delivery(deliveryDate);

		// When
		final boolean isValid = sut.isDeliveryValid(delivery);

		// Then
		assertTrue(isValid); // 부정적인 시나리오
	}

	private static Stream<Arguments> provideArguments() {

		// 이제 더 이상 예상 결과에 대해선 매개변수를 사용하지 않아도 된다.

		return Stream.of(
				Arguments.of(-1),
				Arguments.of(0),
				Arguments.of(1)
		);
	}
}
