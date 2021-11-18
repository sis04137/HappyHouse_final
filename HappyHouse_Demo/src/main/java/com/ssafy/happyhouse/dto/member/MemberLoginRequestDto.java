package com.ssafy.happyhouse.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberLoginRequestDto {
	private String email;
	private String password;
	
	@Builder
	public MemberLoginRequestDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
