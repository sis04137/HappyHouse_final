package com.ssafy.happyhouse.dto.board;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	LocalDateTime regtime;
	
	private String content;
	private int hit;
	
	LocalDateTime modified;
}
