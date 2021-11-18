package com.ssafy.happyhouse.dto.board;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardResponseDto {
	private Long board_id;
	private String title;
	private String content;
	LocalDateTime created;
	LocalDateTime modified;
}
