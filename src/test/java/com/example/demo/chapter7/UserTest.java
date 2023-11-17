package com.example.demo.chapter7;

import com.example.demo.services.crm_system.UserType;
import com.example.demo.services.crm_system.v4.domain.Company;
import com.example.demo.services.crm_system.v4.domain.User;
import com.example.demo.services.crm_system.v6.domain.EmailChangedEvent;
import com.example.demo.services.crm_system.v6.domain.User_with_domain_event;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

	// Version 4
	@Test
	public void changing_email_from_non_corporate_to_corporate() throws Exception {

		// given
		User sut = new User(1, "user@gmail.com", UserType.CUSTOMER);
		Company company = new Company("mycorp.com", 1);

		// when
		sut.changeEmail("new@mycorp.com", company);

		// then
		assertThat(company.getNumberOfEmployees())
				.isEqualTo(2);

		assertThat("new@mycorp.com")
				.isEqualTo(sut.getEmail());

		assertThat(UserType.EMPLOYEE)
				.isEqualTo(sut.getType());
	}

	// version 6
	@Test
	void Changing_email_from_corporate_to_non_corporate() throws Exception {
		// Given
		final Company company = new Company("mycorp.com", 1);
		final User_with_domain_event sut = new User_with_domain_event(1, "user@mycorp.com", UserType.EMPLOYEE, false);

		// When
		sut.changeEmail("new@gmail.com", company);

		// Then
		assertThat(company.getNumberOfEmployees()).isEqualTo(0);
		assertThat(sut.getEmail()).isEqualTo("new@gmail.com");
		assertThat(sut.getType()).isEqualTo(UserType.CUSTOMER);
		assertThat(sut.getEmailChangedEvents()).containsExactly(
				new EmailChangedEvent(1, "new@gmail.com")
		);
	}
}
