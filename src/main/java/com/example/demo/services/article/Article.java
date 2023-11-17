package com.example.demo.services.article;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Article {

	private final List<Comment> comments = new ArrayList<>();

	public void addComment(String text, String author, LocalDateTime now) {
		comments.add(new Comment(text, author, now));
	}

	public Article shouldContainNumberOfComments(int i) {
		return this;
	}
}