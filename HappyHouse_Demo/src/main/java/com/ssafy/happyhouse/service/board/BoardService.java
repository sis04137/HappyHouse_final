package com.ssafy.happyhouse.service.board;

import java.util.List;

import com.github.pagehelper.Page;
import com.ssafy.happyhouse.domain.board.Board;
import com.ssafy.happyhouse.dto.board.BoardRequestDto;
import com.ssafy.happyhouse.dto.board.BoardResponseDto;

public interface BoardService {
	List<BoardResponseDto> findAll();
	List<BoardResponseDto> findAll(String keyword);
	Page<BoardResponseDto> getPagingList(int pageNum);
	BoardResponseDto findById(Long id);
	Long saveNotice(BoardRequestDto requestDto);
	Long updateNotice(Long id, BoardRequestDto requestDto);
	Long deleteNotice(Long id);
	int updateHit(Long id);
}
