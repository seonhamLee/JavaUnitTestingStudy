package com.example.demo.services.message_renderer.domain;

import com.example.demo.services.message_renderer.domain.model.Message;

public class BodyRenderer implements IRenderer {

	@Override
	public String render(Message message) {
		return String.format("<b>%s</b>", message.getBody());
	}
}
