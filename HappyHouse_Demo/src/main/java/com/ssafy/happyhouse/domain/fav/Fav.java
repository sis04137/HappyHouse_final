package com.ssafy.happyhouse.domain.fav;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Fav {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fav_id")
	private Long id;
	
	@Column(name="member_id")
	private Long user_id;
	
	@Column
	private String apt_id;
	
	@Builder
	public Fav(Long id, Long user_id, String apt_id) {
		this.id = id;
		this.user_id = user_id;
		this.apt_id = apt_id;
	}
}
