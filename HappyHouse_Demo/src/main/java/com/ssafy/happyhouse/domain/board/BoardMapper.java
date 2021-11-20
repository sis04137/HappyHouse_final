package com.ssafy.happyhouse.domain.board;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

@Repository
@org.apache.ibatis.annotations.Mapper
public interface BoardMapper {
	@Select("select board_id as id, member_id, title, content, hit, created, modified from board")
	Page<Board> getPagingList();
}
