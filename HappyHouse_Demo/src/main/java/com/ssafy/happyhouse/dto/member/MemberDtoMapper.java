package com.ssafy.happyhouse.dto.member;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ssafy.happyhouse.domain.member.Member;


@Mapper
public interface MemberDtoMapper{
	MemberDtoMapper INSTANCE = Mappers.getMapper(MemberDtoMapper.class);
	
	//Entity에 무조건 Setter붙여줘야 한다 
	Member toEntity(MemberSaveRequestDto requestDto);
	Member toEntity(MemberUpdateRequestDto requestDto);
}
