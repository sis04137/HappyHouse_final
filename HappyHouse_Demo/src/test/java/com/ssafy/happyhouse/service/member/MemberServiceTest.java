package com.ssafy.happyhouse.service.member;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ssafy.happyhouse.domain.member.Member;
import com.ssafy.happyhouse.domain.member.MemberRepository;
import com.ssafy.happyhouse.dto.member.MemberLoginRequestDto;
import com.ssafy.happyhouse.dto.member.MemberResponseDto;
import com.ssafy.happyhouse.dto.member.MemberSaveRequestDto;
import com.ssafy.happyhouse.dto.member.MemberUpdateRequestDto;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {
	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberRepository memberRepository;

	@Transactional
	@Test
	public void saveAndFindById() {
		MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder().address("addr").email("email").name("name")
				.password("pass").tel("tel").build();
		Long id = memberService.saveMember(requestDto);
		
		Member member = memberService.findById(id);
		
		assertEquals(requestDto.getAddress(), member.getAddress());
	}
	
	@Transactional
	@Test
	public void updateMember() {
		MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder().address("addr").email("email").name("name")
				.password("pass").tel("tel").build();
		Long id = memberService.saveMember(requestDto);
		
		//id로 해당 객체 끌어와서 
		MemberUpdateRequestDto requestDto2 = new MemberUpdateRequestDto();
		requestDto2.setPassword(requestDto.getPassword());
		requestDto2.setEmail("wjdrndk96@gmail.com");
		
		Long id2 = memberService.updateUser(id, requestDto2);
		
		assertEquals("wjdrndk96@gmail.com", memberService.findById(id2).getEmail());
	}

	@Transactional
	@Test
	public void findAll() {
		MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder().address("addr").email("email").name("name")
				.password("pass").tel("tel").build();
		Long id = memberService.saveMember(requestDto);
		List<MemberResponseDto> page = memberService.findPaging(1);
		assertEquals(requestDto.getAddress(), page.get(0).getAddress());
		
	}
	
	@Transactional
	@Test
	public void loginTest() {
		MemberLoginRequestDto requestDto = MemberLoginRequestDto.builder().email("wjdrndk96@gmail.com").password("1111").build();
		Member member = memberService.login(requestDto);
		assertEquals(requestDto.getEmail(), member.getEmail());
	}

}
