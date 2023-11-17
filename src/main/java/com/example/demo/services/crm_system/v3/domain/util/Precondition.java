package com.example.demo.services.crm_system.v3.domain.util;

public class Precondition {

	// UserFactory에서 활용하는 안전 장치 기능의 유틸 함수.

	public static void requires(boolean precondition, String message) throws Exception {
		if (!precondition) {
			throw new Exception(message);
		}
	}

	public static void requires(boolean precondition) throws Exception {
		requires(precondition, null);
	}
}