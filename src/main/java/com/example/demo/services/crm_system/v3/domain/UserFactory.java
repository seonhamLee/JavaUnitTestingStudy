package com.example.demo.services.crm_system.v3.domain;


import com.example.demo.services.crm_system.UserType;
import com.example.demo.services.crm_system.v3.domain.util.Precondition;

public class UserFactory {

	// 데이터베이스에서 조회한 데이터를 통해 User를 만든다.

	public static User create(Object[] data) throws Exception {
		Precondition.requires(data.length >= 3);

		final int id = (int) data[0];
		final String email = (String) data[1];
		final UserType type = (UserType) data[2];

		return new User(id, email, type);
	}
}