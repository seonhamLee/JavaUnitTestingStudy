package com.example.demo.chapter4;

import java.util.List;

import com.example.demo.services.message_renderer.domain.BodyRenderer;
import com.example.demo.services.message_renderer.domain.FooterRenderer;
import com.example.demo.services.message_renderer.domain.HeaderRenderer;
import com.example.demo.services.message_renderer.domain.IRenderer;
import com.example.demo.services.message_renderer.domain.MessageRenderer;
import com.example.demo.services.message_renderer.domain.model.Message;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageRendererTest {

	@Test
	public void message_renderer_uses_correct_sub_renderers() {

		// sut의 구현 세부 사항과 결합하여 리팩토링 내성이 깨지는 경우

		MessageRenderer sut = new MessageRenderer();

		List<IRenderer> subRenderers = sut.getSubRenderers(); // 클라이언트의 목표가 아니다.

		assertThat(subRenderers.size()).isEqualTo(3);
		assertThat(subRenderers.get(0)).isInstanceOf(HeaderRenderer.class);
		assertThat(subRenderers.get(1)).isInstanceOf(BodyRenderer.class);
		assertThat(subRenderers.get(2)).isInstanceOf(FooterRenderer.class);
		// subRenderers의 배열 순서를 바꾸는 등 리팩토링 시 검증문은 실패하게 된다. (거짓 양성)
	}

	@Test
	public void rendering_a_message() {

		// sut의 구현 세부 사항 대신 최종 결과를 검증한다.

		// given
		MessageRenderer sut = new MessageRenderer();
		Message message = Message.builder()
				.header("H")
				.body("B")
				.footer("F")
				.build();

		// when
		String result = sut.render(message); // 클라이언트의 목표이다.
		// render 함수에 매개변수가 추가하면 깨지므로, 리팩토링 내성이 만점은 아니다.
		// 하지만 해결하기 쉬운 거짓 양성에 그친다.

		// then
		assertThat(result).isEqualTo("<h1>h</h1><b>b</b><i>f</i>");

		// 리팩토링에 관계없이 클라이언트가 요구하는 동작이 잘 유지되면, 해당 테스트도 보통 잘 통과한다.
	}
}