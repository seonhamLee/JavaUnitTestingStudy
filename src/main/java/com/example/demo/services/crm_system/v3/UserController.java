package com.example.demo.services.crm_system.v3;

import com.example.demo.services.crm_system.v3.domain.User;
import com.example.demo.services.crm_system.v3.domain.UserFactory;
import com.example.demo.services.crm_system.v3.external.Database;
import com.example.demo.services.crm_system.v3.external.MessageBus;

public class UserController {

	// Version 3 : UserFactory의 도입으로 로직이 간결해졌다.

	private final Database database = new Database();

	private final MessageBus messageBus = new MessageBus();

	public void changeEmail(int userId, String newEmail) throws Exception {

		// 데이터베이스에서 조회한 데이터를 통해 User를 만드는 UserFactory를 사용한다.

		final Object[] userData = database.getUserById(userId);
		final User user = UserFactory.create(userData);

		final Object[] companyData = database.getCompany();
		final String companyDomainName = (String) companyData[0];
		final int numberOfEmployees = (int) companyData[1];

		final int newNumberOfEmployees = user.changeEmail(newEmail, companyDomainName, numberOfEmployees);

		database.saveCompany(newNumberOfEmployees);
		database.saveUser(user);
		messageBus.sendEmailChangeMessage(userId, newEmail);
	}
}