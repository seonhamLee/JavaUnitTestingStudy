package com.example.demo.services.crm_system.v6.domain;


import com.example.demo.services.crm_system.v4.domain.Company;
import com.example.demo.services.crm_system.v6.domain.util.Precondition;

public class CompanyFactory {

	public static Company create(Object[] data) throws Exception {
		Precondition.requires(data.length >= 2);

		final String domainName = (String) data[0];
		final int numberOfEmployees = (int) data[1];

		return new Company(domainName, numberOfEmployees);
	}
}