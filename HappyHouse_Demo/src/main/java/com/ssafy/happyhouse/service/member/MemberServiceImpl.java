package com.ssafy.happyhouse.service.member;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssafy.happyhouse.domain.member.Member;
import com.ssafy.happyhouse.domain.member.MemberMapper;
import com.ssafy.happyhouse.domain.member.MemberRepository;
import com.ssafy.happyhouse.dto.member.MemberDtoMapper;
import com.ssafy.happyhouse.dto.member.MemberResponseDto;
import com.ssafy.happyhouse.dto.member.MemberSaveRequestDto;
import com.ssafy.happyhouse.dto.member.MemberUpdateRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor // Autowired없이 생성자로 빈 주입
@Service
public class MemberServiceImpl implements MemberService {
	// bean
//	private final MemberRepository memberRepository;
//	private final MemberMapper memberMapper;

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;
	
	int perPage = 10; // 페이지당 member갯수

	// 페이징처리된 Member List 반환
	@Transactional
	@Override
	public Page<MemberResponseDto> findPaging(int pageNum) {
		PageHelper.startPage(pageNum, perPage);
		Page<MemberResponseDto> page = memberMapper.getPagingList();
		return page;
	}

	//전체리스트(페이징없음)
	@Override
	public List<Member> findAll(){
		return memberRepository.findAll();
	}
	
	// id로 member 찾기
	@Transactional
	@Override
	public Member findById(Long id) {
		Member member = memberMapper.findById(id);
		if (member == null)
			throw new IllegalArgumentException("존재하지 않는 사용자입니다: " + id);
		return member;
	}

	//회원가입
	@Transactional
	@Override
	public Long saveMember(MemberSaveRequestDto requestDto) {
		Long id = memberRepository.save(MemberDtoMapper.INSTANCE.toEntity(requestDto)).getId();
		return id;
	}
	
	//update info : member가 호출하는 함수
	@Transactional
	@Override
	public Long updateUser(Long id, MemberUpdateRequestDto requestDto) {
		Member member = memberRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다. "));
		if(member.getPassword().equals(requestDto.getPassword())) {
			log.debug("PreEmail: {}", member.getEmail());
			member.updateInfo(requestDto);	
			log.debug("PostEmail: {}", member.getEmail());
		}
		else
			throw new IllegalArgumentException("비밀번호가 잘못되었습니다");
		memberRepository.save(member);
		return id;
	}
	
	@Transactional
	@Override
	public Long deleteUser(Long id) {
			memberRepository.deleteById(id);
		return id;
	}

}
