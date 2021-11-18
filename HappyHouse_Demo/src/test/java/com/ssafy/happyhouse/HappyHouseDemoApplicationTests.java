package com.ssafy.happyhouse;


import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@MapperScan(basePackages = "com.ssafy.happyhouse.domain")
@SpringBootTest
class HappyHouseDemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
