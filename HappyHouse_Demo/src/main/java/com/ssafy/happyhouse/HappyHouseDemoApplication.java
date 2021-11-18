package com.ssafy.happyhouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@MapperScan(basePackages = "com.ssafy.happyhouse.domain")
@SpringBootApplication
public class HappyHouseDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappyHouseDemoApplication.class, args);
	}

}
