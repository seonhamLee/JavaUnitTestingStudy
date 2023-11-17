package com.example.demo.services.crm_system.v7.external;

import java.util.HashMap;

import com.example.demo.services.crm_system.v7.domain.Company;
import com.example.demo.services.crm_system.v7.domain.User;

public class Database {

	String connectionString; // 데이터베이스 트랜잭션 개방을 위한 sql

	public Database(String connectionString) {
		this.connectionString = connectionString;
	}

	HashMap<Integer, Object[]> userEntities = new HashMap<>();

	HashMap<Integer, Object[]> companyEntities = new HashMap<>();

	public Object[] getUserById(int userId) {
		return userEntities.get(userId);
	}

	public User getUserByEmail(String email) {
		return null;
	}

	public void saveUser(User user) {

		Object[] userEntity = new Object[3];

		userEntity[0] = user.getUserId();
		userEntity[1] = user.getEmail();
		userEntity[2] = user.getType();

		userEntities.put(user.getUserId(), userEntity);

		// new SqlConnection(connectionString) ... <- 트랜잭션 개방 (챕터 10)
	}

	public Object[] getCompany() {
		return companyEntities.get(0);
	}

	public void saveCompany(Company company) {

		// new SqlConnection(connectionString) ... <- 트랜잭션 개방 (챕터 10)

		Object[] companyEntity = new Object[2];

		companyEntity[0] = company.getDomainName();
		companyEntity[1] = company.getNumberOfEmployees();

		companyEntities.put(0, companyEntity);
	}
}