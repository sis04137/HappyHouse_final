package com.ssafy.happyhouse.dto.board;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ssafy.happyhouse.domain.board.Board;

@Mapper
public interface BoardDtoMapper {
	BoardDtoMapper INSTANCE = Mappers.getMapper(BoardDtoMapper.class);
	
	Board toEntity(BoardRequestDto requestDto);
}
