package com.example.demo.services.customer.repository;

import com.example.demo.services.customer.domain.Product;

public class ProductRepository {

	public String getById(int productId) {
		return Product.shampoo;    // 더미를 반환하는 페이크.
	}
}