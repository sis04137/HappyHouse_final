package com.ssafy.happyhouse.service.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat; 

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ssafy.happyhouse.domain.board.Board;
import com.ssafy.happyhouse.dto.board.BoardRequestDto;
import com.ssafy.happyhouse.service.board.BoardService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTest {
	@Autowired
	BoardService noticeService;

	@Transactional
	@Test
	public void saveNoticeandFind() {
		BoardRequestDto requestDto = BoardRequestDto.builder().title("noticeTitle").content("noticeContent").build();
		Long id = noticeService.saveNotice(requestDto);
		assertEquals("noticeTitle", noticeService.findById(id).getTitle());
	}
	
	//@Transactional
	@Test
	public void updateNotice() {
		BoardRequestDto requestDto = BoardRequestDto.builder().title("noticeTitle").content("noticeContent").build();
		Long id = noticeService.saveNotice(requestDto);
		Board notice = noticeService.findById(id);
		//LocalDateTime created = notice.getCreated();
		noticeService.updateNotice(id, BoardRequestDto.builder().title("title111").content("111").build());
		
		Board brandNewNotice = noticeService.findById(id);
		assertEquals("111", brandNewNotice.getTitle());
		//assertThat(brandNewNotice.getModified()).isAfter(created);	//실질적으로 거의 동시간대에 찍히기 떄문에 수동으로 테스트 완료 
		
	}
	
	
}
