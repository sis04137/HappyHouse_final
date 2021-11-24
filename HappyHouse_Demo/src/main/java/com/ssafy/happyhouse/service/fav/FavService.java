package com.ssafy.happyhouse.service.fav;

import java.util.List;

import com.ssafy.happyhouse.domain.fav.Fav;

public interface FavService {
	Long saveFav(Fav fav);
	Long deleteFav(Long id);
	List<Fav> findFavByUserId(Long user_id);

}
