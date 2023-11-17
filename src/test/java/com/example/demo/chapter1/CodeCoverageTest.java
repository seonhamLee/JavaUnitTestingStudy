package com.example.demo.chapter1;

import com.example.demo.samples.coverage_sample.CodeCoverage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CodeCoverageTest {

	private final CodeCoverage codeCoverage = new CodeCoverage();

	@Test
	public void isStringLong1Test() {

		Boolean result = codeCoverage.isStringLong1("abc");
		// 메서드 isStringLong의 라인 수 : 5

		Assertions.assertFalse(result);
		// 테스트 코드의 라인 수 (중괄호 포함) : 4
		// 코드 커버리지 : 4/5
	}

	@Test
	public void isStringLong2Test() {

		Boolean result = codeCoverage.isStringLong2("abc");
		// 메서드 isStringLong2의 라인 수 : 3

		Assertions.assertFalse(result);
		// 테스트 코드의 라인 수 (중괄호 포함) : 4
		// 코드 커버리지 : 4/3 (테스트 스위트에 대한 개선 없이 제품 코드를 간결화했을 뿐인데 커버리지 지표는 높아진다.)
	}
}