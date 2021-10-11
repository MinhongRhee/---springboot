package com.cos.navernews.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cos.navernews.batch.DataNum;
import com.cos.navernews.domain.NaverNews;
import com.cos.navernews.domain.NaverNewsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NaverNewsCraw {

	private final NaverNewsRepository naverNewsRepository;

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
				String cat = createdAtElement.text();

				String year = cat.substring(0, 4);
				String month = cat.substring(5, 7);
				String day = cat.substring(8, 10);

				String writeday = year + "-" + month + "-" + day; // 작성일 : writeday
				String time = writeday + " 00:00:00";
				Timestamp rawtime = Timestamp.valueOf(time);
				LocalDateTime temp = rawtime.toLocalDateTime().plusHours(9);
				Timestamp createdAt = Timestamp.valueOf(temp);

				LocalDate searchday = LocalDate.now().minusDays(1); // 어제날짜 : searchday
				String date = searchday.toString();
				// System.out.println(company);
				// System.out.println(title);
				// System.out.println("작성일:" + createdAt);
				System.out.println("구하는날짜:" + date);

				if (writeday.equals(date)) {
					NaverNews news = NaverNews.builder().company(company).title(title).createdAt(createdAt).build();

					newsList.add(news);

					System.out.println(newsList);
				} else {
					break;
				}
			} catch (Exception e) {
				System.out.println("존재하지 않는 게시물입니다.");
			}
			DataNum.num++;
		}
		System.out.println(DataNum.num);
		return newsList;
	}

}
