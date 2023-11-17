package com.example.demo.services.message_renderer.domain;

import com.example.demo.services.message_renderer.domain.model.Message;

public class HeaderRenderer implements IRenderer {

	@Override
	public String render(Message message) {
		return String.format("<h1>%s</h1>", message.getHeader());
	}
}
