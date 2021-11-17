package com.ssafy.vue.dto.member;

import javax.persistence.Column;

import com.ssafy.vue.model.member.Member;

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
	
	public Member toEntity() {
		return new Member().builder().user_addr(this.address).user_email(this.email).user_name(this.name).user_pass(this.password)
		.user_tel(this.tel).build();
	}

	
}
