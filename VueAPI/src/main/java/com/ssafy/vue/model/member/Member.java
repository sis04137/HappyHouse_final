package com.ssafy.vue.model.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.autoconfigure.domain.EntityScan;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@Entity
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;
	
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private String name;
	@Column
	private String address;
	@Column
	private String tel;
	
	@Enumerated(EnumType.STRING)
	@Column
	private Role role = Role.MEMBER;

	@Builder
	public Member(String user_email, String user_pass, String user_name, String user_addr,
			String user_tel) {
		this.email = user_email;
		this.password = user_pass;
		this.name = user_name;
		this.address = user_addr;
		this.tel = user_tel;
	}
	
	//null이 아닌 값 있으면 싹 주기
	//update member information 
	public void updateInfo(com.ssafy.vue.dto.member.MemberUpdateRequestDto requestDto) {
		
		if(requestDto.getAddress() != null) this.address = requestDto.getAddress();
		if(requestDto.getEmail() != null) {this.email = requestDto.getEmail();
			//System.out.println(this.email);
		}
		if(requestDto.getName() != null) {this.name = requestDto.getName();}
		if(requestDto.getPassword() != null) this.password = requestDto.getPassword();
		if(requestDto.getTel() != null) this.tel = requestDto.getTel();
	}
	
	
}
