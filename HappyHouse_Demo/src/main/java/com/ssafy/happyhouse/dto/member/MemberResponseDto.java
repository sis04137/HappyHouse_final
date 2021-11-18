package com.ssafy.happyhouse.dto.member;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ssafy.happyhouse.domain.member.Member;
import com.ssafy.happyhouse.domain.member.Role;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MemberResponseDto {
	private Long member_id;
	private String email;
	private String password;
	private String name;
	private String address;
	private String tel;
	private Role role;
	
}
