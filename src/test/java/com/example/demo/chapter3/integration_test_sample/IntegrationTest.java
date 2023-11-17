package com.example.demo.chapter3.integration_test_sample;

import com.example.demo.samples.integration_test_sample.Database;

public abstract class IntegrationTest {

	protected final Database database;

	public IntegrationTest() {

		this.database = new Database();
	}

	public void dispose() {

		database.dispose();
	}
}
