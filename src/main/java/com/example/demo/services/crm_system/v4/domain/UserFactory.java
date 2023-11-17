package com.example.demo.services.crm_system.v4.domain;

import com.example.demo.services.crm_system.UserType;
import com.example.demo.services.crm_system.v4.domain.util.Precondition;

public class UserFactory {

	public static User create(Object[] data) throws Exception {

		// 도메인 유의성이 없으므로 테스트해볼 가치가 없는 조건에 해당된다.
		Precondition.requires(data.length >= 3);

		final int id = (int) data[0];
		final String email = (String) data[1];
		final UserType type = (UserType) data[2];

		return new User(id, email, type);
	}
}