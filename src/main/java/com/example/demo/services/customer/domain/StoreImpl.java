package com.example.demo.services.customer.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StoreImpl implements Store {

	private Map<String, Integer> inventory = new HashMap<String, Integer>() {
		{
			put(Product.shampoo, 100); // dummy
		}
	};

	@Override
	public Boolean hasEnoughInventory(String product, Integer quantity) {

		return getInventory(product) >= quantity;
	}

	@Override
	public void removeInventory(String product, Integer quantity) {

		if (!hasEnoughInventory(product, quantity)) {
			throw new IllegalArgumentException("Not enough inventory");
		}
		inventory.merge(product, quantity, (src, val) -> src - val);
	}

	@Override
	public void addInventory(String product, Integer quantity) {

		inventory.put(
				product,
				inventory.getOrDefault(product, 0) + quantity
		);
	}

	@Override
	public Integer getInventory(String product) {

		return inventory.get(product);
	}
}
