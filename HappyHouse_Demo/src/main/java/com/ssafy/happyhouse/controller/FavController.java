package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.domain.fav.Fav;
import com.ssafy.happyhouse.dto.board.BoardRequestDto;
import com.ssafy.happyhouse.dto.fav.FavRequestDto;
import com.ssafy.happyhouse.service.fav.FavService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/fav")
@RequiredArgsConstructor
@RestController
public class FavController {
	
	private final FavService favService;
	
	@PostMapping("/save")
	ResponseEntity<?> saveFav(@RequestBody FavRequestDto requestDto){
		return new ResponseEntity<Long>(favService.saveFav(requestDto), HttpStatus.OK);
	}
	
	@GetMapping("/delete/{fav_id}")
	ResponseEntity<?> deleteFav(@PathVariable Long fav_id){
		return new ResponseEntity<Long>(favService.deleteFav(fav_id), HttpStatus.OK);
	}
	
	@GetMapping("{user_id}")
	ResponseEntity<?> getFav(@PathVariable Long user_id){
		return new ResponseEntity<List<Fav>>(favService.findFavByUserId(user_id), HttpStatus.OK);
	}
	
	

}
