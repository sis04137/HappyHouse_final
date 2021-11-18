package com.ssafy.happyhouse.dto.board;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.github.pagehelper.Page;
import com.ssafy.happyhouse.domain.board.Board;

@org.mapstruct.Mapper
public interface BoardDtoMapper {
	BoardDtoMapper INSTANCE = Mappers.getMapper(BoardDtoMapper.class);
	
	@Mapping(source = "userid", target = "member_id")
	@Mapping(source = "subject", target = "title")
	Board toEntity(BoardRequestDto requestDto);
	
	
	@Mapping(source = "member_id", target = "userid")
	@Mapping(source = "id", target = "articleno")
	@Mapping(source = "title", target = "subject")
	@Mapping(source = "created", target = "regtime")
	List<BoardResponseDto> toDtoList(List<Board> requestEntity);
	
	@Mapping(source = "member_id", target = "userid")
	@Mapping(source = "id", target = "articleno")
	@Mapping(source = "title", target = "subject")
	@Mapping(source = "created", target = "regtime")
	Page<BoardResponseDto> toDtoList(Page<BoardResponseDto> requestEntity);
	
	@Mapping(source = "member_id", target = "userid")
	@Mapping(source = "id", target = "articleno")
	@Mapping(source = "title", target = "subject")
	@Mapping(source = "created", target = "regtime")
	BoardResponseDto toDto(Board requestEntity);
	
}
