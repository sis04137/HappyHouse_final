package com.ssafy.happyhouse.service.comment;

import java.util.List;

import com.ssafy.happyhouse.domain.comment.Comment;


public interface CommentService {
	List<Comment> list(Long board_id);
	Long create(Comment commentDto);
	Long modify(Comment commentDto);
	Long delete(Long id);	
}
