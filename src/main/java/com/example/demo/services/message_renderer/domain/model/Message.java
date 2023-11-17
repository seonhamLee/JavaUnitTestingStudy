package com.example.demo.services.message_renderer.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Message {

	private String header;

	private String body;

	private String footer;
}
