package com.example.demo.services.crm_system.v5;

import com.example.demo.services.crm_system.v5.domain.Company;
import com.example.demo.services.crm_system.v5.domain.CompanyFactory;
import com.example.demo.services.crm_system.v5.domain.User;
import com.example.demo.services.crm_system.v5.domain.UserFactory;
import com.example.demo.services.crm_system.v5.external.Database;
import com.example.demo.services.crm_system.v5.external.MessageBus;

public class UserController {

	// Version 5 : CanExecute/Execute 패턴 사용

	private final Database database = new Database();

	private final MessageBus messageBus = new MessageBus();

	public String changeEmail(int userId, String newEmail) throws Exception {
		final Object[] userData = database.getUserById(userId);
		final User user = UserFactory.create(userData);

		// User 속성 확인 로직은 User 의 메서드로 두어 캡슐화를 지킨다.
		final String error = user.canChangeEmail(); // CanExecute

		// 해당 분기는 테스트할 가치가 없다.
		// User의 Precondition 덕에, canChangeEmail 호출을 안하고 changeEmail을 호출할 경우, 즉시 버그가 드러난다. (빠른 실패 원칙)
		if (error != null) {
			return error;
		}

		// 이메일 변경 가능 여부를 먼저 보고 Company 인스턴스를 조회한다 -> 성능 저하 X

		final Object[] companyData = database.getCompany();
		final Company company = CompanyFactory.create(companyData);

		user.changeEmail(newEmail, company); // Execute

		database.saveCompany(company);
		database.saveUser(user);

		// user가 이메일을 변경하지 않아도 변경 메세지를 보내는 문제가 남아있다. (v6에서 해결)
		messageBus.sendEmailChangeMessage(userId, newEmail);

		return "OK";
	}
}