package com.ssafy.happyhouse.service.fav;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.domain.fav.Fav;
import com.ssafy.happyhouse.domain.fav.FavRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FavServiceImpl implements FavService{
	
	private final FavRepository favRepository;
	
	@Transactional
	@Override
	public Long saveFav(Fav fav) {
		return favRepository.save(fav).getId();
	}

	@Transactional
	@Override
	public Long deleteFav(Long id) {
		Fav fav = favRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 찜이 없습니다."));
		favRepository.delete(fav);
		return id;
	}

	@Transactional
	@Override
	public List<Fav> findFavByUserId(Long user_id) {
		return favRepository.findFavUserId(user_id);
	}

}
