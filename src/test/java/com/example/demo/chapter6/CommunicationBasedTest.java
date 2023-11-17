package com.example.demo.chapter6;

import com.example.demo.services.email_gateway.IEmailGateway;
import com.example.demo.services.user.Controller;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;

public class CommunicationBasedTest {

	// 통신 기반 테스트 사례.

	@Test
	void Sending_a_greeting_email() throws Exception {

		// Given
		final String userEmail = "user@userEmail.com";
		final IEmailGateway emailGatewayMock = mock(IEmailGateway.class);
		final Controller sut = new Controller(emailGatewayMock);

		// When
		sut.greetUser(userEmail);

		// Then
		then(emailGatewayMock).should(only()).sendGreetingsEmail(userEmail);
	}
}
