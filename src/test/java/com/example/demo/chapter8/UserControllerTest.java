package com.example.demo.chapter8;

import com.example.demo.services.crm_system.IBus;
import com.example.demo.services.crm_system.UserType;
import com.example.demo.services.crm_system.v7.UserController;
import com.example.demo.services.crm_system.v7.domain.Company;
import com.example.demo.services.crm_system.v7.domain.CompanyFactory;
import com.example.demo.services.crm_system.v7.domain.User;
import com.example.demo.services.crm_system.v7.domain.UserFactory;
import com.example.demo.services.crm_system.v7.external.Database;
import com.example.demo.services.crm_system.v7.external.MessageBus;
import com.example.demo.services.crm_system.v9.BusSpy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class UserControllerTest {

	// 1. 주요 흐름 물색 : 사용자와 회사 데이터베이스가 모두 업데이트된다.

	// 2. 예외 상황 조건 : 이메일을 변경할 수 없는 시나리오의 경우, 빠른 실패 원칙을 준수하므로 테스트하지 않는다.

	// 3. 목으로 대체할 프로세스 외부 의존성 : 데이터베이스는 관리 의존성이므로 목으로 검증하지 않는다. 메시지 버스는 비관리 의존성이므로 목으로 검증한다.

	@Test
	public void changing_email_from_corporate_to_non_corporate() throws Exception { // 사전에 정해둔 주요 흐름을 테스트한다.

		// given
		Database db = new Database("connection string");
		User user = createUser("user@mycorp.com", UserType.EMPLOYEE, db);
		createCompany("mycorp.com", 1, db);

		final MessageBus messageBusMock = mock(MessageBus.class);

		UserController sut = new UserController(db, messageBusMock);

		// when
		String result = sut.changeEmail(user.getUserId(), "new@gmail.com");

		// then
		Assertions.assertEquals("OK", result);

		Object[] userFromDb = db.getUserById(user.getUserId());
		User userData = UserFactory.create(userFromDb); // 관리 의존성이므로 최종 상태만을 검증한다

		Assertions.assertEquals("new@gmail.com", userData.getEmail());
		Assertions.assertEquals(UserType.CUSTOMER, userData.getType());

		Object[] companyFromDb = db.getCompany();
		Company companyData = CompanyFactory.create(companyFromDb); // 관리 의존성이므로 최종 상태만을 검증한다
		Assertions.assertEquals(0, companyData.getNumberOfEmployees());

		then(messageBusMock) // 비관리 의존성이므로 목을 통해 상호 작용을 검증한다.
				.should(times(1))
				.sendEmailChangeMessage(user.getUserId(), "new@gmail.com");
	}

	// V8. (챕터9) IBus를 목으로 대체한다.
	@Test
	public void changing_email_from_corporate_to_non_corporate_v8() throws Exception {

		// given
		Database db = new Database("connection string");
		User user = createUser("user@mycorp.com", UserType.EMPLOYEE, db);
		createCompany("mycorp.com", 1, db);

		final IBus busMock = mock(IBus.class); // 외부 의존성의 가장 먼 곳을 목으로 대체한다.
		MessageBus messageBus = new MessageBus(busMock); // 사이에 낀 외부 의존성은 구체 클래스를 사용한다.

		UserController sut = new UserController(db, messageBus);

		// when
		String result = sut.changeEmail(user.getUserId(), "new@gmail.com");

		// then
		Assertions.assertEquals("OK", result);

		Object[] userFromDb = db.getUserById(user.getUserId());
		User userData = UserFactory.create(userFromDb);

		Assertions.assertEquals("new@gmail.com", userData.getEmail());
		Assertions.assertEquals(UserType.CUSTOMER, userData.getType());

		Object[] companyFromDb = db.getCompany();
		Company companyData = CompanyFactory.create(companyFromDb);
		Assertions.assertEquals(0, companyData.getNumberOfEmployees());

		// 실제 클라이언트가 식별할 수 있는 텍스트를 검증할 수 있게 된다.
		then(busMock)
				.should(times(1)) // 항상 목 호출 횟수를 검증하라
				.send(String.format("Subject: USER; Type: EMAIL CHANGED; Id: %d; NewEmail: %s", userData.getUserId(), userData.getEmail()));
	}

	// V9. (챕터9) IBus에 목 대신 스파이를 활용한다.
	@Test
	public void changing_email_from_corporate_to_non_corporate_v9() throws Exception {

		// given
		Database db = new Database("connection string");
		User user = createUser("user@mycorp.com", UserType.EMPLOYEE, db);
		createCompany("mycorp.com", 1, db);

		BusSpy busSpy = new BusSpy(); // 외부 의존성의 가장 끝 단은 목 대신, 직접 구현한 스파이로 대체한다.
		MessageBus messageBus = new MessageBus(busSpy);

		UserController sut = new UserController(db, messageBus);

		// when
		String result = sut.changeEmail(user.getUserId(), "new@gmail.com");

		// then
		Assertions.assertEquals("OK", result);

		Object[] userFromDb = db.getUserById(user.getUserId());
		User userData = UserFactory.create(userFromDb);

		Assertions.assertEquals("new@gmail.com", userData.getEmail());
		Assertions.assertEquals(UserType.CUSTOMER, userData.getType());

		Object[] companyFromDb = db.getCompany();
		Company companyData = CompanyFactory.create(companyFromDb);
		Assertions.assertEquals(0, companyData.getNumberOfEmployees());

		// 실제 클라이언트가 식별할 수 있는 텍스트를 그대로 검증하면서, 더 간결해졌다.
		busSpy.shouldSendNumberOfMessages(1)
				.withEmailChangedMessage(user.getUserId(), "new@gmail.com");
	}

	// 아래 헬퍼 메서드를 통해 준비 구절을 간결화했다.

	private User createUser(
			String email,
			UserType type,
			Database db
	) throws Exception {

		db.saveUser(new User(
						1,
						email,
						type,
						false
				)
		);

		return UserFactory.create(db.getUserById(1));
	}

	private void createCompany(
			String domainName,
			int numberOfEmployees,
			Database db
	) {

		db.saveCompany(new Company(
						domainName,
						numberOfEmployees
				)
		);
	}
}
