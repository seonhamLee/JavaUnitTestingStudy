package com.example.demo.services.crm_system.v4.domain.util;

public class Precondition {

	// 요구사항에 맞지 않을 경우 예외를 던진다.

	public static void requires(boolean precondition, String message) throws Exception {
		if (!precondition) {
			throw new Exception(message);
		}
	}

	public static void requires(boolean precondition) throws Exception {
		requires(precondition, null);
	}
}