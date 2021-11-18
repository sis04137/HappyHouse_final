package com.ssafy.happyhouse.dto.board;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardResponseDto {
	//board_id
	private Long articleno;
	//title
	private String subject;
	//created
	LocalDateTime regtime;
	
	private String content;
	private int hit;
	
	LocalDateTime modified;
}
