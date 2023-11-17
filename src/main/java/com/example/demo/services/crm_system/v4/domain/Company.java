package com.example.demo.services.crm_system.v4.domain;

import com.example.demo.services.crm_system.v4.domain.util.Precondition;
import lombok.Getter;

@Getter
public class Company {

	private final String domainName;

	private int numberOfEmployees;

	public Company(String domainName, int numberOfEmployees) {
		this.domainName = domainName;
		this.numberOfEmployees = numberOfEmployees;
	}

	public void changeNumberOfEmployees(int delta) throws Exception {

		// 버그 발생 시의 보호장치. 도메인 유의성이 있으므로 테스트해볼 조건에 해당된다.
		Precondition.requires(numberOfEmployees + delta >= 0);
		numberOfEmployees += delta;
	}

	public boolean isEmailCorporate(String email) {
		String emailDomain = email.split("@")[1];
		return emailDomain.equals(domainName);
	}
}