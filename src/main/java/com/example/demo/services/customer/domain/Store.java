package com.example.demo.services.customer.domain;

public interface Store {

	Boolean hasEnoughInventory(String product, Integer quantity);

	void removeInventory(String product, Integer quantity);

	void addInventory(String product, Integer quantity);

	Integer getInventory(String product);
}
