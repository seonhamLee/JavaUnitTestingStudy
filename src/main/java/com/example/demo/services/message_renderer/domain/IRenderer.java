package com.example.demo.services.message_renderer.domain;

import com.example.demo.services.message_renderer.domain.model.Message;

public interface IRenderer {

	String render(Message message);
}
