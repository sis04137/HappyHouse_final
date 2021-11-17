package com.ssafy.vue.model.service.member;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.dto.member.MemberLoginRequestDto;
import com.ssafy.vue.dto.member.MemberSaveRequestDto;
import com.ssafy.vue.dto.member.MemberUpdateRequestDto;
import com.ssafy.vue.model.member.Member;
import com.ssafy.vue.model.member.MemberRepository;

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
	
	int perPage = 10; // 페이지당 member갯수


	//전체리스트(페이징없음)
	@Override
	public List<Member> findAll(){
		return memberRepository.findAll();
	}
	
	// id로 member 찾기
	@Transactional
	@Override
	public Member findById(Long id) {
		Member member = memberRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다. "));
		return member;
	}

	//회원가입
	@Transactional
	@Override
	public Long saveMember(MemberSaveRequestDto requestDto) {
		Long id = memberRepository.save(requestDto.toEntity()).getId();
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
	
	@Transactional
	@Override
	public Member login(MemberLoginRequestDto requestDto) {
		Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다. "));
		if(member.getPassword().equals(requestDto.getPassword())) {
			return member;
		}
		else {
			throw new IllegalArgumentException("비밀번호가 잘못되었습니다.");
		}
	}

}
