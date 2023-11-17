package com.example.demo.chapter3.reuse_test_fixture_samples.anti_pattern;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import com.example.demo.samples.reuse_test_fixture_sample.Delivery;
import com.example.demo.samples.reuse_test_fixture_sample.DeliveryService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertSame;

// 테스트 매개변수로 입력값과 예상 결과를 모두 넣었을 경우를 보여준다.
public class TooMuchParameterizedTest {

	@ParameterizedTest
	@MethodSource("provideArguments")
	public void Can_detect_an_invalid_delivery_date(
			int daysFromNow, boolean expected
	) {
		// Given
		final DeliveryService sut = new DeliveryService();
		final LocalDateTime deliveryDate = LocalDateTime.now().plusDays(daysFromNow);
		final Delivery delivery = new Delivery(deliveryDate);

		// When
		final boolean isValid = sut.isDeliveryValid(delivery);

		// Then
		assertSame(expected, isValid);

		// 한번의 테스트로 많은 분기를 체크하지만 가독성이 떨어진다.
	}

	private static Stream<Arguments> provideArguments() {
		return Stream.of(
				Arguments.of(-1, false),
				Arguments.of(0, false),
				Arguments.of(1, false),
				Arguments.of(2, true)
		);
	}
}
