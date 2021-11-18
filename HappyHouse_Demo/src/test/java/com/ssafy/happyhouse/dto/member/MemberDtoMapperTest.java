package com.ssafy.happyhouse.dto.member;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.ssafy.happyhouse.domain.member.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberDtoMapperTest {

	@Test
	public void toEntity() {
		MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
				.name("test").tel("test").address("test").email("test").password("test")
				.build();
		Member member = MemberDtoMapper.INSTANCE.toEntity(requestDto);
		assertEquals(member.getEmail(), "test");
	}
}
