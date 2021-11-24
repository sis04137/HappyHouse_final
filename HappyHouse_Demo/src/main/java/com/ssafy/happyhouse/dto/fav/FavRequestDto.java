package com.ssafy.happyhouse.dto.fav;

import com.ssafy.happyhouse.domain.fav.Fav;

import lombok.Data;

@Data
public class FavRequestDto {
	public Long user_id;
	public String apt_id;
	
	public Fav toEntity() {
		Fav fav = new Fav();
		fav.setApt_id(apt_id);
		fav.setUser_id(user_id);
		return fav;
	}
}
