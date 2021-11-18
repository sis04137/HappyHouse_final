package com.ssafy.happyhouse.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDto {
	
	
	Long userid;
	String subject;
	String content;
	
	
	@Builder
	public BoardRequestDto(String subject, String content, Long userid) {
		this.subject = subject;
		this.content = content;
		this.userid = userid;
	}
}
