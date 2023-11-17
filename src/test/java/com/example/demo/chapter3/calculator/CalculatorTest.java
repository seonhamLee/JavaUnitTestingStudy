package com.example.demo.chapter3.calculator;

import com.example.demo.services.calculator.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

	private final Calculator calculator;


	// JUnit에선 클래스 내 각 테스트마다, 테스트 실행 전 아래 생성자를 호출한다.
	public CalculatorTest() {
		this.calculator = new Calculator();
	}

	@Test
	public void sum_of_two_numbers() {

		// arrange(= given)
		final Integer first = 10;
		final Integer second = 10;

		// act(= when)
		Integer result = calculator.Sum(first, second);

		// assert(= then)
		Assertions.assertEquals(result, 20);
	}
}