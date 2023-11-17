package com.example.demo.chapter6;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.services.article.Article;
import com.example.demo.services.article.Comment;
import com.example.demo.services.order_product.Order;
import com.example.demo.services.order_product.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StatusBasedTest {

	@Test
	void Adding_a_product_to_an_order() throws Exception {

		// 상태 기반 테스트의 사례.

		// Given
		final Product product = new Product("Hand wash");
		final Order sut = new Order();

		// When
		sut.addProduct(product);

		// Then
		assertThat(sut.getProducts().size()).isEqualTo(1);
		assertThat(sut.getProducts().get(0)).isEqualTo(product);
	}

	@Test
	void Adding_a_comment_to_an_article() throws Exception {

		// sut에 댓글을 추가하면, 댓글 목록에 댓글이 나타나는지를 확인한다.

		// Given
		final Article sut = new Article();
		final String text = "Comment text";
		final String author = "John Doe";
		final LocalDateTime now = LocalDateTime.now();

		// When
		sut.addComment(text, author, now);

		// Then
		assertThat(sut.getComments().size()).isEqualTo(1);
		assertThat(sut.getComments().get(0).text).isEqualTo(text);
		assertThat(sut.getComments().get(0).author).isEqualTo(author);
		assertThat(sut.getComments().get(0).dateCreated).isEqualTo(now);

		// 상태 기반 테스트의 경우 검증할 상태 폭에 따라 이와같이 검증문이 커질 수 있다.
	}

	@Test
	void Adding_a_comment_to_an_article2() throws Exception {

		// 아래 헬퍼 메서드를 두어 검증문을 간결화했다.

		// Given
		final Article sut = new Article();
		final String text = "Comment text";
		final String author = "John Doe";
		final LocalDateTime now = LocalDateTime.now();

		// When
		sut.addComment(text, author, now);

		// Then
		shouldContainNumberOfComments(sut, 1);
		withComment(sut, text, author, now);
	}

	@Test
	void Adding_a_comment_to_an_article3() throws Exception {

		// 라이브러리의 컬렉션 비교 메서드를 활용해서 검증문을 간결화했다.

		// Given
		final Article sut = new Article();
		final Comment comment = new Comment(
				"Comment text",
				"John Doe",
				LocalDateTime.now()
		);

		// When
		sut.addComment(comment.text, comment.author, comment.dateCreated);

		// Then
		assertThat(sut.getComments()).containsExactly(comment);
	}

	// 헬퍼 메서드

	private void shouldContainNumberOfComments(Article article, int commentCount) {
		assertThat(article.getComments().size()).isEqualTo(1);
	}

	private void withComment(Article article, String text, String author, LocalDateTime dateCreated) {
		final List<Comment> comments = article.getComments();
		final Comment comment = getSingleOrDefault(comments, text, author, dateCreated);
		assertThat(comment).isNotNull();
	}

	private Comment getSingleOrDefault(List<Comment> comments, String text, String author, LocalDateTime dateCreated) {
		if (comments.size() > 1) {
			throw new IllegalStateException();
		}
		if (comments.isEmpty()) {
			return new Comment(text, author, dateCreated);
		}
		return comments.get(0);
	}
}
