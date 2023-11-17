package com.example.demo.services.user;

import com.example.demo.services.email_gateway.IEmailGateway;
import com.example.demo.services.user.domain.Report;
import com.example.demo.services.user.repository.IDatabase;

public class Controller {

	private IEmailGateway emailGateway;

	private IDatabase database;

	public Controller(IEmailGateway emailGateway) {
		this.emailGateway = emailGateway;
	}

	public Controller(IDatabase database) {
		this.database = database;
	}

	public void greetUser(String userEmail) {
		emailGateway.sendGreetingsEmail(userEmail);
	}

	public Report createReport() {
		final int numberOfUsers = database.getNumberOfUsers();
		return new Report(numberOfUsers);
	}
}