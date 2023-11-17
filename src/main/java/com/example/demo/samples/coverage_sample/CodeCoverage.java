package com.example.demo.samples.coverage_sample;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CodeCoverage { // 코드 커버리지. 테스트 커버리지(test coverage)라고도 한다.

	public Boolean isStringLong1(String input) {

		if (input.length() > 5) {
			return true;
		}

		return false;
		// 전체 코드 라인 수는 (중괄호 포함) 5이다.
	}

	public Boolean isStringLong2(String input) {

		return input.length() > 5;
		// 전체 코드 라인 수는  (중괄호 포함) 3이다.
	}
}
