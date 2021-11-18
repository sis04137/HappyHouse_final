package com.ssafy.happyhouse.domain.board;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.ssafy.happyhouse.dto.board.BoardResponseDto;

@Repository
@org.apache.ibatis.annotations.Mapper
public interface BoardMapper {
	@Select("select * from board")
	Page<BoardResponseDto> getPagingList();
}
