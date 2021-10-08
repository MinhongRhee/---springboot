package com.cos.navernews.domain;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import reactor.core.publisher.Flux;

public interface NaverNewsRepository extends ReactiveMongoRepository<NaverNews, String> {

	// db.runCommand( { convertToCapped: 'naver_realtime', size: 8192 } ) 
	@Tailable // 커서를 계속 열어두는 어노테이션
	@Query("{ }") // find 내부의 쿼리
	Flux<NaverNews> mFindAll();


}
