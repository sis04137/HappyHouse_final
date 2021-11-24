package com.ssafy.happyhouse.service.fav;

import java.util.List;

import com.ssafy.happyhouse.domain.fav.Fav;
import com.ssafy.happyhouse.dto.fav.FavRequestDto;

public interface FavService {
	Long saveFav(FavRequestDto requestDto);
	Long deleteFav(Long id);
	List<Fav> findFavByUserId(Long user_id);
	Long deleteFavInMap(Long user_id, String apt_id);
}
