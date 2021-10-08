package com.cos.navernews.domain;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
@Document(collection = "naver_realtime")
public class NaverNews {
	@Id private String _id;
	private String company;
	private String title;
	private Timestamp createdAt;
}
