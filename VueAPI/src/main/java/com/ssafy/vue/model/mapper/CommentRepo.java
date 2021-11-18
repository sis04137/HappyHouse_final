package com.ssafy.vue.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.CommentDto;

@Mapper
public interface CommentRepo {
	List<CommentDto> list(int articleno);
	int create(CommentDto commentDto);
	int modify(CommentDto commentDto);
	int delete(int commentNo);	
}
