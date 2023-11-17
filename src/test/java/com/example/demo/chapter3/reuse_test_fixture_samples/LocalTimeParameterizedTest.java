package com.example.demo.chapter3.reuse_test_fixture_samples;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import com.example.demo.samples.reuse_test_fixture_sample.Delivery;
import com.example.demo.samples.reuse_test_fixture_sample.DeliveryService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertSame;

// 책에선 C#에서 테스트 매개변수가 외부 라이브러리 의존성일 경우를 별도로 다룬다.
// 자바에서는 해당 경우 외에도 비공개 정적 메서드 사용을 지향하므로, 큰 변화가 없다.
public class LocalTimeParameterizedTest {

	@ParameterizedTest
	@MethodSource("provideArguments")
	public void Can_detect_an_invalid_delivery_date(
			LocalDateTime deliveryDate, boolean expected
	) {
		// Given
		final DeliveryService sut = new DeliveryService();
		final Delivery delivery = new Delivery(deliveryDate);

		// When
		final boolean isValid = sut.isDeliveryValid(delivery);

		// Then
		assertSame(expected, isValid);
	}

	private static Stream<Arguments> provideArguments() {
		return Stream.of(
				Arguments.of(LocalDateTime.now().minusDays(1L), false),
				Arguments.of(LocalDateTime.now(), false),
				Arguments.of(LocalDateTime.now().plusDays(1L), false),
				Arguments.of(LocalDateTime.now().plusDays(2L), true)
		);
	}
}
