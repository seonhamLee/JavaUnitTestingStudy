package com.example.demo.services.message_renderer.domain;

import com.example.demo.services.message_renderer.domain.model.Message;

public class FooterRenderer implements IRenderer {

	@Override
	public String render(Message message) {
		return String.format("<i>%s</i>", message.getFooter());
	}
}
