package com.cos.navernews;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Test20211008ApplicationTests {

	@Test
	void contextLoads() {
		String cat = "2021.10.10. 오후 7:22";
		String year = cat.substring(0, 4);
		String month = cat.substring(5, 7);
		String day = cat.substring(8, 10);
		String hourCheck = cat.substring(12,14);
		String hour = cat.substring(15,17);
		String minute = cat.substring(17,19);
		System.out.println(minute);
	}

}
