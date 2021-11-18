package com.ssafy.happyhouse.dto.board;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ssafy.happyhouse.domain.board.Board;

@Mapper
public interface BoardDtoMapper {
	BoardDtoMapper INSTANCE = Mappers.getMapper(BoardDtoMapper.class);
	
	Board toEntity(BoardRequestDto requestDto);
	
	@Mapping(source = "id", target = "articleno")
	@Mapping(source = "title", target = "subject")
	@Mapping(source = "created", target = "regtime")
	List<BoardResponseDto> toDtoList(List<Board> requestEntity);
	
	@Mapping(source = "id", target = "articleno")
	@Mapping(source = "title", target = "subject")
	@Mapping(source = "created", target = "regtime")
	BoardResponseDto toDto(Board requestEntity);
}
