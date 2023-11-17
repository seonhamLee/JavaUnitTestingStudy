package com.example.demo.services.message_renderer.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.services.message_renderer.domain.model.Message;
import lombok.Getter;

@Getter
public class MessageRenderer implements IRenderer {

	private final List<IRenderer> subRenderers;

	public MessageRenderer() {
		this.subRenderers = List.of(
				new HeaderRenderer(),
				new BodyRenderer(),
				new FooterRenderer()
		);
	}

	@Override
	public String render(Message message) {
		return subRenderers.stream()
				.map(x -> x.render(message))
				.collect(Collectors.joining());
	}
}
