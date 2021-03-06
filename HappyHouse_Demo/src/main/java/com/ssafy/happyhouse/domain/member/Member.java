package com.ssafy.happyhouse.domain.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import com.ssafy.happyhouse.dto.member.MemberUpdateRequestDto;

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

	@Column
	String picture;

	@Enumerated(EnumType.STRING)
	@Column
	private Role role = Role.MEMBER;

	@Builder
	public Member(String user_email, String user_pass, String user_name, String user_addr, String user_tel) {
		this.email = user_email;
		this.password = user_pass;
		this.name = user_name;
		this.address = user_addr;
		this.tel = user_tel;
	}
	
	@Builder
	public Member(String user_email, String user_pass, String user_name, String user_addr, String user_tel, String picture) {
		this.email = user_email;
		this.password = user_pass;
		this.name = user_name;
		this.address = user_addr;
		this.tel = user_tel;
		this.picture = picture;
	}
	
	
	@Builder
	public Member(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

	// null이 아닌 값 있으면 싹 주기
	// update member information
	public void updateInfo(MemberUpdateRequestDto requestDto) {

		if (requestDto.getAddress() != null)
			this.address = requestDto.getAddress();
		if (requestDto.getEmail() != null) {
			this.email = requestDto.getEmail();
			// System.out.println(this.email);
		}
		if (requestDto.getName() != null) {
			this.name = requestDto.getName();
		}
		if (requestDto.getPassword() != null)
			this.password = requestDto.getPassword();
		if (requestDto.getTel() != null)
			this.tel = requestDto.getTel();
	}
	
	public Member updateAuth(String name, String picture) {
		this.picture = picture;
		this.name = name;
		return this;
	}

}
