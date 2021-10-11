package com.cos.navernews.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cos.navernews.batch.DataNum;
import com.cos.navernews.domain.NaverNews;

@Component
public class NaverNewsCraw {

	public List<NaverNews> collect() {
		RestTemplate rt = new RestTemplate();
		List<NaverNews> newsList = new ArrayList<>();

		while (true) {
			String aid = String.format("%010d", DataNum.num);
			String url = "https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=103&oid=437&aid=" + aid;
			try {
				String html = rt.getForObject(url, String.class);

				Document doc = Jsoup.parse(html);

				Element companyElement = doc.selectFirst(".press_logo img");
				Element titleElement = doc.selectFirst("#articleTitle");
				Element createdAtElement = doc.selectFirst(".t11");

				String company = companyElement.attr("title");
				String title = titleElement.text();
				String cat = createdAtElement.text().trim();

				String year = cat.substring(0, 4);
				String month = cat.substring(5, 7);
				String day = cat.substring(8, 10);
				String hourCheck = cat.substring(12, 14); // 오전, 오후 구분

				String hour =  cat.substring(14, 17); // 시간 두자리로 짜르기
				
				String minute = null;
				
				String[] h = hour.trim().split(":");

					switch (hourCheck) {
					case "오후":
						int h1 = Integer.parseInt(h[0]) + 12;
						if (h1 < 22) {
							hour = String.valueOf(h1);
							minute = cat.substring(17, 19);
						}
						if (h1 > 21) {
							hour = String.valueOf(h1);
							minute = cat.substring(18, 20);
						}
						if (h1 == 24) {
							hour = String.valueOf("12");
							minute = cat.substring(18, 20);
						}
					case "오전":
						int h2 = Integer.parseInt(h[0]);
						if (h2 < 10) {
							hour = String.valueOf(h2);
							minute = cat.substring(17, 19);
						}
						if (h2 > 9) {
							hour = String.valueOf(h2);
							minute = cat.substring(18, 20);
						}
					}
					
					String testday = year + "-" + month + "-" + day; // 기사 작성일 (시간 포함x)
					
					String writeday = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + "00"; // 기사 작성일 (시간포함)
					Timestamp rawtime = Timestamp.valueOf(writeday);
					LocalDateTime temp = rawtime.toLocalDateTime().plusHours(9);
					Timestamp dd = Timestamp.valueOf(temp); // 2021-10-11 16:29:00.0
					Date createdAt = new Date(dd.getTime()); // Mon Oct 11 16:29:00 KST 2021

					LocalDate searchday = LocalDate.now().minusDays(1); // 어제날짜 : searchday
					String date = searchday.toString();

					if (testday.equals(date)) {
						NaverNews news = NaverNews.builder().company(company).title(title).createdAt(createdAt).build();

						newsList.add(news);

					} else {
						break;
					}
			} catch (Exception e) {
				System.out.println("존재하지 않는 게시물입니다.");
			}
			DataNum.num++;
		} // while 종료
		
		System.out.println(DataNum.num);
		return newsList;

	}
}
