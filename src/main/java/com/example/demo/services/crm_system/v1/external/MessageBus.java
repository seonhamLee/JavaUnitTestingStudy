package com.example.demo.services.crm_system.v1.external;


import com.example.demo.services.crm_system.IBus;

public class MessageBus {

	private IBus bus;

	public void sendEmailChangeMessage(int userId, String newEmail) {
		bus.send(String.format("Subject: USER; Type: EMAIL CHANGED; Id: %d; NewEmail: %s", userId, newEmail));
	}
}