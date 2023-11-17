package com.example.demo.services.crm_system.v4;

import com.example.demo.services.crm_system.v4.domain.Company;
import com.example.demo.services.crm_system.v4.domain.CompanyFactory;
import com.example.demo.services.crm_system.v4.domain.User;
import com.example.demo.services.crm_system.v4.domain.UserFactory;
import com.example.demo.services.crm_system.v4.external.Database;
import com.example.demo.services.crm_system.v4.external.MessageBus;

public class UserController {

	private final Database database = new Database();

	private final MessageBus messageBus = new MessageBus();

	public void changeEmail(int userId, String newEmail) throws Exception {

		Object[] userData = database.getUserById(userId);
		User user = UserFactory.create(userData);

		Object[] companyData = database.getCompany();
		Company company = CompanyFactory.create(companyData);

		user.changeEmail(newEmail, company);

		database.saveCompany(company);
		database.saveUser(user);
		messageBus.sendEmailChangeMessage(userId, newEmail);
	}
}