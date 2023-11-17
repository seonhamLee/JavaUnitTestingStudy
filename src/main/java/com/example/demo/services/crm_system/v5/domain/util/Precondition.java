package com.example.demo.services.crm_system.v5.domain.util;

public class Precondition {

	public static void requires(boolean precondition, String message) throws Exception {
		if (!precondition) {
			throw new Exception(message);
		}
	}

	public static void requires(boolean precondition) throws Exception {
		requires(precondition, null);
	}
}