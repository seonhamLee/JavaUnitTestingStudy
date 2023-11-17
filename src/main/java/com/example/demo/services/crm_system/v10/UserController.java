package com.example.demo.services.crm_system.v10;

import com.example.demo.services.crm_system.v7.domain.Company;
import com.example.demo.services.crm_system.v7.domain.CompanyFactory;
import com.example.demo.services.crm_system.v7.domain.EmailChangedEvent;
import com.example.demo.services.crm_system.v7.domain.User;
import com.example.demo.services.crm_system.v7.domain.UserFactory;
import com.example.demo.services.crm_system.v7.external.Database;
import com.example.demo.services.crm_system.v7.external.MessageBus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserController {

	// version 7. 챕터 8에서 다룬다.

	private Database database; // 의존성 주입

	private MessageBus messageBus;

	public String changeEmail(int userId, String newEmail) throws Exception {

		// 데이터베이스에서 유저 정보를 가져와, 애플리케이션 내에서 쓰는 유저로 인스턴스화한다.
		final Object[] userData = database.getUserById(userId);
		final User user = UserFactory.create(userData);

		// 해당 유저가 이메일을 변경할 수 있는지 체크한다.
		final String error = user.canChangeEmail();
		if (error != null) {
			return error;
		}

		// 데이터베이스에서 회사 정보를 가져와, 애플리케이션 내에서 쓰는 회사로 인스턴스화한다.
		final Object[] companyData = database.getCompany();
		final Company company = CompanyFactory.create(companyData);

		// 유저 이메일을 변경하는데, 사원으로 변경되면 애플리케이션 내 회사 정보의 사원 수가 바뀐다.
		user.changeEmail(newEmail, company);

		// 바뀐 사원수를 데이터베이스에 전달한다.
		database.saveCompany(company);

		// 바뀐 유저 정보를 데이터베이스에 전달한다.
		database.saveUser(user);

		// 도메인 이벤트 처리
		for (final EmailChangedEvent ev : user.getEmailChangedEvents()) {
			messageBus.sendEmailChangeMessage(ev.getUserId(), ev.getNewEmail());
		}

		return "OK";
	}
}