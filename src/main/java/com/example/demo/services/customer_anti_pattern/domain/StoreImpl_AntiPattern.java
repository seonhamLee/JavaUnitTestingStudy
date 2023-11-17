package com.example.demo.services.customer_anti_pattern.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StoreImpl_AntiPattern implements Store_AntiPattern {

	private Map<String, Integer> inventory = new HashMap<>();

	@Override
	public Boolean hasEnoughInventory(String product, Integer quantity) {

		return getInventory(product) >= quantity;
	}

	@Override
	public void removeInventory(Boolean canRemove, String product, Integer quantity) {


		if (!canRemove) {
			throw new IllegalArgumentException("Not enough inventory");
		}
		inventory.merge(product, quantity, (src, val) -> src - val);
	}

	public void addInventory(String product, Integer quantity) {

		inventory.put(
				product,
				inventory.getOrDefault(product, 0) + quantity
		);
	}

	public Integer getInventory(String product) {

		return inventory.get(product);
	}
}
