package com.ssafy.happyhouse.dto.comment;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ssafy.happyhouse.domain.comment.Comment;
import com.ssafy.happyhouse.dto.board.BoardDtoMapper;

@org.mapstruct.Mapper
public interface CommentDtoMapper {
	CommentDtoMapper INSTANCE = Mappers.getMapper(CommentDtoMapper.class);
	
	@Mapping(source = "comment_no", target = "id")
	@Mapping(source = "user_id", target = "member_id")
	@Mapping(source = "comment_time", target = "created")
	@Mapping(source = "isbn", target = "bid")
	Comment toEntity(CommentDto requestDto);
	
	@Mapping(source = "id", target = "comment_no")
	@Mapping(source = "member_id", target = "user_id")
	@Mapping(source = "created", target = "comment_time")
	@Mapping(source = "bid", target = "isbn")
	CommentDto toDto(Comment requestEntity);
}
