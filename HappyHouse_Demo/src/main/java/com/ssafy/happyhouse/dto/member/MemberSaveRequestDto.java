package com.ssafy.happyhouse.dto.member;

import javax.persistence.Column;

import com.ssafy.happyhouse.domain.member.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {
	private String email;
	private String password;
	private String name;
	private String address;
	private String tel;
	
	@Builder
	public MemberSaveRequestDto(String email, String password, String name, String address, String tel) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.address = address;
		this.tel = tel;
	}

	
}
