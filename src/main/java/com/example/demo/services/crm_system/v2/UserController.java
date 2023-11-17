package com.example.demo.services.crm_system.v2;

import com.example.demo.services.crm_system.UserType;
import com.example.demo.services.crm_system.v2.domain.User;
import com.example.demo.services.crm_system.v2.external.Database;
import com.example.demo.services.crm_system.v2.external.MessageBus;

public class UserController {

	// Version 2 : 험블 객체

	// 외부 의존성 주입이 아닌, 직접 인스턴스화. UserController 테스트 작성 시 문제가 될 것이다.
	private final Database database = new Database();

	private final MessageBus messageBus = new MessageBus();

	public void changeEmail(int userId, String newEmail) {

		// 아직 로직이 꽤 복잡하다.

		final Object[] data = database.getUserById(userId);
		final String email = (String) data[1];
		final UserType type = (UserType) data[2];
		final User user = new User(userId, email, type);

		final Object[] companyData = database.getCompany();
		final String companyDomainName = (String) companyData[0];
		final int numberOfEmployees = (int) companyData[1];

		final int newNumberOfEmployees = user.changeEmail(newEmail, companyDomainName, numberOfEmployees);

		database.saveCompany(newNumberOfEmployees);
		database.saveUser(user);
		messageBus.sendEmailChangeMessage(userId, newEmail);
	}

}