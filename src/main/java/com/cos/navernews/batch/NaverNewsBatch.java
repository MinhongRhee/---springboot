package com.cos.navernews.batch;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cos.navernews.domain.NaverNews;
import com.cos.navernews.domain.NaverNewsRepository;
import com.cos.navernews.util.NaverNewsCraw;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NaverNewsBatch {
	
	private final NaverNewsRepository naverNewsRepository;
	private final NaverNewsCraw naverNewsCraw;
	
	@Scheduled(fixedDelay = 1000*2*1)
	//@Scheduled(cron = "0 0 1 * * *", zone="Asia/Seoul")
	public void testCount() {
		
		List<NaverNews> newsList = naverNewsCraw.collect();
		System.out.println(newsList);
		naverNewsRepository.saveAll(newsList);
		System.out.println(naverNewsRepository.saveAll(newsList));
		
	}

}