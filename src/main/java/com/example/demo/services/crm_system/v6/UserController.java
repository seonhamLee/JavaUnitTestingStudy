package com.example.demo.services.crm_system.v6;

import com.example.demo.services.crm_system.v4.domain.Company;
import com.example.demo.services.crm_system.v6.domain.CompanyFactory;
import com.example.demo.services.crm_system.v6.domain.EmailChangedEvent;
import com.example.demo.services.crm_system.v6.domain.UserFactory;
import com.example.demo.services.crm_system.v6.domain.User_with_domain_event;
import com.example.demo.services.crm_system.v6.external.Database;
import com.example.demo.services.crm_system.v6.external.MessageBus;

public class UserController {

	private final Database database = new Database();

	private final MessageBus messageBus = new MessageBus();

	public String changeEmail(int userId, String newEmail) throws Exception {
		final Object[] userData = database.getUserById(userId);
		final User_with_domain_event user = UserFactory.create(userData);

		//
		final String error = user.canChangeEmail();
		if (error != null) {
			return error;
		}

		final Object[] companyData = database.getCompany();
		final Company company = CompanyFactory.create(companyData);

		user.changeEmail(newEmail, company);

		database.saveCompany(company);
		database.saveUser(user);

		// 도메인 이벤트 처리
		for (final EmailChangedEvent ev : user.getEmailChangedEvents()) {
			messageBus.sendEmailChangeMessage(ev.getUserId(), ev.getNewEmail());
		}

		return "OK";
	}
}