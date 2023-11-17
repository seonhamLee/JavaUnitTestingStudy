package com.example.demo.services.customer_anti_pattern.domain;

public interface Store_AntiPattern {

	Boolean hasEnoughInventory(String product, Integer quantity);

	void removeInventory(Boolean canRemove, String product, Integer quantity);

	void addInventory(String product, Integer quantity);

	Integer getInventory(String product);
}
