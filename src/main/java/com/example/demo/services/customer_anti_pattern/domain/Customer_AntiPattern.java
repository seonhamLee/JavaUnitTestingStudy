package com.example.demo.services.customer_anti_pattern.domain;

public class Customer_AntiPattern {

	public Boolean purchase(Store_AntiPattern store, String product, Integer quantity) {

		return store.hasEnoughInventory(product, quantity);
		// 고객은 해당 메서드 호출만으로 정상 동작하길 원하지만, 위와 같은 구현은 이를 위반한다.
	}
}
