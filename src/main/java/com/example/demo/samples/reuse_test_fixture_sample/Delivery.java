package com.example.demo.samples.reuse_test_fixture_sample;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Delivery {

	private LocalDateTime date;

	public Delivery(LocalDateTime date) {
		this.date = date;
	}
}
