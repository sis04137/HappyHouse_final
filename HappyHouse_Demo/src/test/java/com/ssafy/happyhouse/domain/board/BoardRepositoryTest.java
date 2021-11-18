package com.ssafy.happyhouse.domain.board;

import static org.assertj.core.api.Assertions.assertThat; 

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ssafy.happyhouse.domain.board.Board;
import com.ssafy.happyhouse.domain.board.BoardRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTest {
	
	@Autowired
	BoardRepository noticeRepository;
	
	@Test
	public void SaveBaseTimeEntityTest() {
		LocalDateTime now = LocalDateTime.of(2021, 11, 10, 0, 0, 0);
		Long id = noticeRepository.save(Board.builder().title("title1").content("content1").build()).getId();
		List<Board> list = noticeRepository.findAll();
		Board notice = list.get(list.size()-1);
		log.info(">>>>>>created: {}, modified: {}", notice.getCreated(), notice.getModified());
		
		assertThat(notice.getCreated()).isAfter(now);		
		assertThat(notice.getModified()).isAfter(now);
		
	}
	
	@Test
	public void saveNotice() {
		
	}
}
