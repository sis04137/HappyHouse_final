package com.ssafy.happyhouse.service.comment;

import java.util.List;

import com.ssafy.happyhouse.domain.comment.Comment;
import com.ssafy.happyhouse.dto.comment.CommentDto;
import com.ssafy.happyhouse.dto.comment.CommentRequestDto;


public interface CommentService {
	List<CommentDto> list(Long board_id);
	Long create(CommentRequestDto commentDto);
	Long modify(CommentRequestDto commentDto);
	Long delete(Long id);	
}
