package com.cos.navernews.batch;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cos.navernews.domain.NaverNews;
import com.cos.navernews.domain.NaverNewsRepository;
import com.cos.navernews.util.NaverNewsCraw;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class NaverNewsBatch {
	
	private final NaverNewsRepository naverNewsRepository;
	private final NaverNewsCraw naverNewsCraw;
	
	@Scheduled(fixedDelay = 1000*60*1)
	//@Scheduled(cron = "* 3 9 * * *", zone="Asia/Seoul")
	public void testCount() {
		
		List<NaverNews> newsList = naverNewsCraw.collect();
		System.out.println(newsList);
		
		Flux.fromIterable(newsList)
		.flatMap(this.naverNewsRepository::save)
		.doOnComplete(() -> System.out.println("Complete")).subscribe();
		
		System.out.println("크롤링 완료:" + newsList.size());		
		System.out.println("세이브 완료");
	}

}