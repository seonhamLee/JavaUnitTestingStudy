package com.example.demo.services.email_gateway;

public interface IEmailGateway {

	void sendGreetingsEmail(String userEmail);

	void sendReceipt(String email, String productName, int quantity);
}