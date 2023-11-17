package com.example.demo.samples.coverage_sample;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BranchCoverage {

	public static Boolean wasLastStringLong; // 제품 메서드의 암묵적 결과를 반영한다.

	public Boolean isStringLong1(String input) {

		return input.length() > 5; // 해당 분기로 두 가지 경로가 발생한다.

		// 메서드의 결과는 하나이다.
	}

	public Boolean isStringLong2(String input) {

		Boolean result = input.length() > 5; // 해당 분기로 두 가지 경로가 발생한다.

		wasLastStringLong = result; // 암묵적 결과

		return result; // 명시적 결과

		// 메서드의 결과는 둘이다.
	}

	public Integer parse(String input) {

		return Integer.parseInt(input);
		// 외부 라이브러리(Integer)의 parseInt로 인해 발생한 경로는 여러 가지이다.
	}
}