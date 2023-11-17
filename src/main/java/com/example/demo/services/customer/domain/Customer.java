package com.example.demo.services.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Customer {

	private String email;

	public Boolean purchase(Store store, String product, Integer quantity) {

		if (!store.hasEnoughInventory(product, quantity)) {
			return false;
		}

		store.removeInventory(
				product,
				store.getInventory(product) - quantity
		);

		return true;
	}
}
