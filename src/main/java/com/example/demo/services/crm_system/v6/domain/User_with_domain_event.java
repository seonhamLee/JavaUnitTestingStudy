package com.example.demo.services.crm_system.v6.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.services.crm_system.UserType;
import com.example.demo.services.crm_system.v4.domain.Company;
import com.example.demo.services.crm_system.v6.domain.util.Precondition;
import lombok.Getter;

@Getter
public class User_with_domain_event {

	private int userId;

	private String email;

	private UserType type;

	private boolean isEmailConfirmed;

	private List<EmailChangedEvent> emailChangedEvents; // 도메인 이벤트

	public User_with_domain_event(int userId, String email, UserType type, boolean isEmailConfirmed) {
		this.userId = userId;
		this.email = email;
		this.type = type;
		this.isEmailConfirmed = isEmailConfirmed;
		emailChangedEvents = new ArrayList<>();
	}

	public String canChangeEmail() {
		if (isEmailConfirmed) {
			return "Can't change email after it's confirmed";
		}
		return null;
	}

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

		// 이메일이 변경되었으므로 도메인이벤트 속성에 추가한다.
		emailChangedEvents.add(new EmailChangedEvent(userId, newEmail));
	}
}