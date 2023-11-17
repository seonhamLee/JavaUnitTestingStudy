package com.example.demo.services.crm_system.v7.external;


import com.example.demo.services.crm_system.IBus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageBus {

	private IBus bus;

	public void sendEmailChangeMessage(int userId, String newEmail) {
		bus.send(String.format("Subject: USER; Type: EMAIL CHANGED; Id: %d; NewEmail: %s", userId, newEmail));
	}
}