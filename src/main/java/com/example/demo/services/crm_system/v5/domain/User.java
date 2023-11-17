package com.example.demo.services.crm_system.v5.domain;

import com.example.demo.services.crm_system.UserType;
import com.example.demo.services.crm_system.v5.domain.util.Precondition;
import lombok.Getter;

@Getter
public class User {

	// Version 5 : CanExecute/Execute 패턴 사용

	private int userId;

	private String email;

	private UserType type;

	private boolean isEmailConfirmed; // 컨트롤러로부터 받는 요구 사항

	public User(int userId, String email, UserType type, boolean isEmailConfirmed) {
		this.userId = userId;
		this.email = email;
		this.type = type;
		this.isEmailConfirmed = isEmailConfirmed;
	}
	
	// CanExecute
	public String canChangeEmail() {

		// 사용자가 확인한 후에 이메일을 변경하려 하면 오류를 표시한다.
		if (isEmailConfirmed) {
			return "Can't change email after it's confirmed";
		}
		return null;
	}

	// Execute
	public void changeEmail(String newEmail, Company company) throws Exception {
		Precondition.requires(canChangeEmail() == null);

		if (email.equals(newEmail)) {
			return;
		}

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