package com.example.demo.chapter1;

import com.example.demo.samples.coverage_sample.BranchCoverage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BranchCoverageTest { // 해당 테스트는 분기 커버리지 지표의 한계를 보여주고 있다.

	private final BranchCoverage branchCoverage = new BranchCoverage();

	@Test
	public void isStringLong1Test1() {

		Boolean result = branchCoverage.isStringLong1("abc");
		// 제품 메서드의 분기로 인한 두가지 경로 중 한가지만을 테스트했다.

		Assertions.assertFalse(result);
		// 분기 커버리지 : 1 / 2
	}

	@Test
	public void isStringLong1Test2() {

		Boolean result1 = branchCoverage.isStringLong1("abc");
		Boolean result2 = branchCoverage.isStringLong1("abcdef");
		// 제품 메서드의 분기로 인한 두가지 경로를 모두 테스트했다.

		Assertions.assertFalse(result1);
		Assertions.assertTrue(result2);
		// 분기 커버리지 : 2 / 2
	}

	@Test
	public void isStringLong1Test3() {

		Boolean result1 = branchCoverage.isStringLong1("abc");
		Boolean result2 = branchCoverage.isStringLong1("abcdef");
		// 제품 메서드의 분기로 인한 두가지 경로를 모두 테스트했다.

		// 검증문은 존재하지 않는다.

		// 분기 커버리지 : 2 / 2 (검증문 없이도 모든 경로를 테스트했으므로, 분기 커버리지는 만점이다.)
	}

	@Test
	public void isStringLong2Test() {

		Boolean result1 = branchCoverage.isStringLong2("abc");
		Boolean result2 = branchCoverage.isStringLong1("abcdef");
		// 제품 메서드의 분기로 인한 두가지 경로를 모두 테스트했다.
		// 제품 메서드의 두가지 결과 중 한가지만을 테스트했다.

		Assertions.assertFalse(result1);
		Assertions.assertTrue(result2);
		// 분기 커버리지 : 2 / 2 (제품 메서드의 모든 결과를 테스트하지 않았어도, 분기 커버리지는 만점이다.)
	}

	@Test
	public void parseTest() {

		Integer result = branchCoverage.parse("5");
		// 제품 메서드에는 명시된 분기가 없다. (경로는 하나)
		// 제품 메서드에서 활용하는 외부 라이브러리 메서드에는 분기가 존재한다.

		Assertions.assertEquals(result, 5);
		// 분기 커버리지 : 1 / 1 (외부 라이브러리에 존재하는 분기에 대한 판단이 이뤄지지 않았다.)
	}
}