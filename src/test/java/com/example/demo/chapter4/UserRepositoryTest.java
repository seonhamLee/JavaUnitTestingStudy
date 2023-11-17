package com.example.demo.chapter4;

import com.example.demo.services.inquary_user.User;
import com.example.demo.services.inquary_user.UserRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest {

	@Test
	public void get_by_id_executes_correct_sql_code() {

		UserRepository sut = new UserRepository();

		User user = sut.getById(5);

		assertThat(sut.getLastExecutedSqlStatement())
				.isEqualTo("dummy SQL");
		// 각기 같은 동작을 하는 sql문이 여러개 존재하므로, 선택(리팩토링)에 따라 헤당 검증문이 실패한다.
		// 회귀 방지와 빠른 피드백은 우수하나, 리팩토링 내성이 부족하다.
	}
}