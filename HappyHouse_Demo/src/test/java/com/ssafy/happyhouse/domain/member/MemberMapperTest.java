package com.ssafy.happyhouse.domain.member;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mysql.cj.log.Log;
import com.ssafy.happyhouse.dto.member.MemberResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberMapperTest {
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	MemberRepository memberRepository;
	
	@Transactional
	@Test
	public void selectAllPaging() {
		for(int i = 1; i <= 40; i++) {
			String e = "email"+ i;
			Member account = Member.builder()
					.user_name("test")
					.user_pass("test")
					.user_email(e)
					.user_addr("test")
					.user_tel("test")
					.build();
			memberRepository.save(account);
		}
		int perPage = 5;									//페이지당 글 갯수
		
		PageHelper.startPage(1, perPage);
		Page<MemberResponseDto> page = memberMapper.getPagingList();
//		PageHelper.startPage(2, perPage);
//		page = memberMapper.getPagingList();
//		PageHelper.startPage(3, perPage);
//		page = memberMapper.getPagingList();
		
		Long total = page.getTotal();						//전체 row count 구하기 
		assertEquals(total/perPage, page.getPages());		//getPages()로 페이지수 구하기 
	}
	
	@Transactional
	@Test
	public void findById() {
		Member account = Member.builder()
				.user_name("test")
				.user_pass("test")
				.user_email("test")
				.user_addr("test")
				.user_tel("test")
				.build();
		Long id = memberRepository.save(account).getId();
		Member member = memberMapper.findById(id);
		assertEquals(account.getEmail(), member.getEmail());
	}
}
