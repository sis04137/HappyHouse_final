package com.ssafy.happyhouse.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginRequestDto {
	private String email;
	private String password;
}
