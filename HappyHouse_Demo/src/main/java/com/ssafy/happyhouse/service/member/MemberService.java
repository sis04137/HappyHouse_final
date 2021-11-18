package com.ssafy.happyhouse.service.member;

import java.util.List;

import com.github.pagehelper.Page;
import com.ssafy.happyhouse.domain.member.Member;
import com.ssafy.happyhouse.dto.member.MemberLoginRequestDto;
import com.ssafy.happyhouse.dto.member.MemberResponseDto;
import com.ssafy.happyhouse.dto.member.MemberSaveRequestDto;
import com.ssafy.happyhouse.dto.member.MemberUpdateRequestDto;

public interface MemberService {
	
	//paging 처리됨 정확한 return은 Page<Member>
	Page<MemberResponseDto> findPaging(int pageNum);
	
	//전체리스트
	List<Member> findAll();

	// id로 member 찾기
	Member findById(Long id);

	//회원가입
	Long saveMember(MemberSaveRequestDto requestDto);
	
	//update info : member가 호출하는 함수
	Long updateUser(Long id, MemberUpdateRequestDto requestDto);
	
	Long deleteUser(Long id);

	
	Member login(MemberLoginRequestDto requestDto);
}
