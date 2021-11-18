package com.ssafy.happyhouse.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {
	String title;
	String content;
	Long member_id;
	
	
	@Builder
	public BoardRequestDto(String title, String content, Long id) {
		this.title = title;
		this.content = content;
		this.member_id = id;
	}
}
