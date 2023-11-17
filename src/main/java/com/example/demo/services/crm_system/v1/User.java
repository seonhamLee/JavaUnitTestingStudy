package com.example.demo.services.crm_system.v1;

import java.util.NoSuchElementException;

import com.example.demo.services.crm_system.UserType;
import com.example.demo.services.crm_system.v1.external.Database;
import com.example.demo.services.crm_system.v1.external.MessageBus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {

	// Version 1 : 지나치게 복잡한 코드에 해당한다.

	private int userId;

	private String email;

	private UserType type;

	// Version 1.5 : 외부 의존성 주입을 통해, User 테스트 시 목으로 처리할 수 있게된다.

	private final Database database;

	private final MessageBus messageBus;

	public void changeEmail(int userId, String newEmail) throws NoSuchElementException {

		// 데이터베이스에서 사용자의 현재 이메일과 유형 검색한다
		final Object[] data = database.getUserById(userId);
		if (data == null) {
			throw new NoSuchElementException();
		}
		this.userId = userId;
		this.email = (String) data[1];
		this.type = (UserType) data[2];

		// 바꾸려는 이메일과 기존 것이 다르지 않다면 중지한다.
		if (this.email.equals(newEmail)) {
			return;
		}

		// 데이터베이스에서 조직의 도메인 이름과 직원 수를 검색한다
		final Object[] companyData = database.getCompany();
		if (companyData == null) {
			throw new NoSuchElementException();
		}
		final String companyDomainName = (String) companyData[0];
		final int numberOfEmployees = (int) companyData[1];

		// 새 이메일의 도메인 이름에 따라 사용자 유형을 설정한다.
		final String emailDomain = newEmail.split("@")[1];
		final boolean isEmailCorporate = emailDomain.equals(companyDomainName);
		final UserType newType = isEmailCorporate
				? UserType.EMPLOYEE
				: UserType.CUSTOMER;

		// 조직의 직원 수 변동 여부를 체크하고, 필요 시 업데이트한다.
		if (this.type != newType) {
			final int delta = newType == UserType.EMPLOYEE ? 1 : -1;
			final int newNumber = numberOfEmployees + delta;
			database.saveCompany(newNumber);
		}

		this.email = newEmail;
		this.type = newType;

		// 데이터베이스에 사용자를 저장한다
		database.saveUser(this); // 외부 협력자에 의존한다.

		// 메시지버스에 알림을 전송한다
		messageBus.sendEmailChangeMessage(this.userId, newEmail); // 외부 협력자에 의존한다.
	}
}