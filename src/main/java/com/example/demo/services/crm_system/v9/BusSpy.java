package com.example.demo.services.crm_system.v9;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.services.crm_system.IBus;

public class BusSpy implements IBus {

	// 스파이. (직접 작성한 목)

	private final List<String> sentMessages = new ArrayList<>();

	@Override
	public void send(String message) {
		sentMessages.add(message);
	}

	public BusSpy shouldSendNumberOfMessages(int number) throws Exception {

		// 항상 목 호출 횟수를 검증하라

		if (number != sentMessages.size()) {
			throw new Exception();
		}

		return this;
	}

	public BusSpy withEmailChangedMessage(int userId, String newEmail) throws Exception {

		String message = String.format("Subject: USER; Type: EMAIL CHANGED; Id: %d; NewEmail: %s", userId, newEmail);

		if (!sentMessages.contains(message)) {
			throw new Exception();
		}

		return this;
	}
}
