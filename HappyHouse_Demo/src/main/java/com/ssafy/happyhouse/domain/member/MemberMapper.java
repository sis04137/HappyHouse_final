package com.ssafy.happyhouse.domain.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.ssafy.happyhouse.dto.member.MemberResponseDto;

@Repository
@Mapper
public interface MemberMapper {
	
	
	@Select("select * from member")
	Page<MemberResponseDto> getPagingList();
	
	Member findById(Long id);
	
}
