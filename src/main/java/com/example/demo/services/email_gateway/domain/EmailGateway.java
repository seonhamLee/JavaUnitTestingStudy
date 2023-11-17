package com.example.demo.services.email_gateway.domain;

import com.example.demo.services.email_gateway.IEmailGateway;

public class EmailGateway implements IEmailGateway {

	@Override
	public void sendGreetingsEmail(String userEmail) {
	}

	@Override
	public void sendReceipt(String email, String productName, int quantity) {
	}
}