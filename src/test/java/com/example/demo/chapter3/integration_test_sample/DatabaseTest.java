package com.example.demo.chapter3.integration_test_sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DatabaseTest extends IntegrationTest { // 기반 클래스 상속을 통해 database 인스턴스에 접근한다.

	@Test
	void testWithExtends() throws Exception {
		assertNotNull(database);
	}

	// 아래 추가되는 테스트들도 처음 한번 인스턴스화된 database 로 접근한다.
}