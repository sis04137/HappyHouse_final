package com.ssafy.happyhouse.dto.board;

import com.github.pagehelper.Page;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponsePagingDto {
	//id
	public Page<BoardResponseDto> list;
	public int totalPage;
	
	@Builder
	public BoardResponsePagingDto(Page<BoardResponseDto> list, int totalPage) {
		this.list = list;
		this.totalPage = totalPage;
	}
	
}