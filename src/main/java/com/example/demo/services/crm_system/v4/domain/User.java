package com.example.demo.services.crm_system.v4.domain;

import com.example.demo.services.crm_system.UserType;
import lombok.Getter;

@Getter
public class User {

	// Version 4 : 사원 관련 로직은 이제 Company로 다 빠젔다. (SRP를 준수)

	private final int userId;

	private String email;

	private UserType type;

	// 이러한 생성자는 '간단한 코드' 사분면에 속하며, 테스트할 가치가 없다.
	public User(int userId, String email, UserType type) {
		this.userId = userId;
		this.email = email;
		this.type = type;
	}

	// User에 새로운 의존성(Company)이 생겼다
	public void changeEmail(String newEmail, Company company) throws Exception {
		if (email.equals(newEmail)) {
			return;
		}

		// 회사 관련 로직은 Company 인스턴스로 대체된다.
		UserType newType = company.isEmailCorporate(newEmail)
				? UserType.EMPLOYEE
				: UserType.CUSTOMER;

		if (type != newType) {
			int delta = newType == UserType.EMPLOYEE ? 1 : -1;
			company.changeNumberOfEmployees(delta);
		}

		email = newEmail;
		type = newType;
	}
}