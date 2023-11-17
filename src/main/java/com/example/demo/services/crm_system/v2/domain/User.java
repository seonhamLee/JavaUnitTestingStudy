package com.example.demo.services.crm_system.v2.domain;

import com.example.demo.services.crm_system.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

	// Version 2 : 이제 User는 더 이상 외부 협력자에 의존하지 않는다.

	private final int userId;

	private String email;

	private UserType type;

	public int changeEmail(String newEmail, String companyDomainName, int numberOfEmployees) {
		if (this.email.equals(newEmail)) {
			return numberOfEmployees;
		}

		final String emailDomain = newEmail.split("@")[1];
		final boolean isEmailCorporate = emailDomain.equals(companyDomainName);
		final UserType newType = isEmailCorporate
				? UserType.EMPLOYEE
				: UserType.CUSTOMER;

		if (this.type != newType) {
			final int delta = newType == UserType.EMPLOYEE ? 1 : -1;
			numberOfEmployees = numberOfEmployees + delta;
		}

		this.email = newEmail;
		this.type = newType;

		// User와 별 관련 없어 보이는 회사 직원 수를 반환한다. (SRP 위뱨)
		return numberOfEmployees;
	}
}