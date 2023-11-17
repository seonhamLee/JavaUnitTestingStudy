package com.example.demo.chapter5;

import com.example.demo.services.email_gateway.IEmailGateway;
import com.example.demo.services.user.Controller;
import com.example.demo.services.user.domain.Report;
import com.example.demo.services.user.repository.IDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@ExtendWith(MockitoExtension.class) // 행동 규정이 안된 mock이 null 포인터가 되지 않게 도와준다.
class ControllerTest {

	// '목(mock)'에 대한 용어 혼동을 조심하자 !

	@Mock
	IEmailGateway mock; // mock 라이브러리를 통해 만든 목.
	// 해당 의존성은 sut에 의해 상태가 변경된다.

	@Mock
	IDatabase stub; // mock 라이브러리를 통해 만든 스텁.
	// 해당 의존성은 sut의 입력 데이터(numberOfUsers)를 가져다 준다.

	@Test
	void sending_a_greetings_email() {

		// 해당 테스트는 외부로 나가는 상호 작용에 목을 잘 활용한 사례를 다룬다.

		// Given
		final String email = "user@email.com";
		final Controller sut = new Controller(mock); // 외부 의존성 주입

		// When
		sut.greetUser(email);

		// Then
		then(mock).should(only()).sendGreetingsEmail(email);
		// 외부로 나가는 상호 작용을 검증하고 있다.
		// 외부 의존성(목)의 구현 세부 사항에 결합된다.
	}

	@Test
	void creating_a_report() {

		// 해당 테스트는 sut의 입력 데이터에 대해 스텁을 잘 활용한 사례를 다룬다.

		// Given
		given(stub.getNumberOfUsers()).willReturn(10); // 스텁과의 상호 작용 규정(모방).
		final Controller sut = new Controller(stub); // sut의 입력 데이터에 스텁을 활용한다.

		// When
		final Report report = sut.createReport();

		// Then
		assertThat(report.getNumberOfUsers()).isEqualTo(10);
		// 내부로 들어오는 상호 작용은 검증하지 않는다.
		// sut의 최종 결과만을 검증하고 있다.
	}


	@Test
	void creating_a_report2() {

		// 해당 테스트는 sut의 입력 데이터와의 상호 작용을 검증한 사례를 다룬다.

		// Given
		given(stub.getNumberOfUsers()).willReturn(10);
		final Controller sut = new Controller(stub);

		// When
		final Report report = sut.createReport();

		// Then
		assertThat(report.getNumberOfUsers()).isEqualTo(10);
		then(stub).should(only()).getNumberOfUsers();
		// 내부로 들어오는 상호 작용을 검증하고 있다.(과잉 명세)
	}
}