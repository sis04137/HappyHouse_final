package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.ssafy.happyhouse.domain.board.Board;
import com.ssafy.happyhouse.dto.board.BoardRequestDto;
import com.ssafy.happyhouse.dto.board.BoardResponseDto;
import com.ssafy.happyhouse.service.board.BoardService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {
	private final BoardService boardService;
	
	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다.")
	@PostMapping
	ResponseEntity<?> saveNotice(@RequestBody BoardRequestDto requestDto){
		log.info("userid: {}", requestDto.getUserid());
		return new ResponseEntity<Long>(boardService.saveNotice(requestDto), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping
	ResponseEntity<?> findAll(){
		return new ResponseEntity<List<BoardResponseDto>>(boardService.findAll(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 페이지 반환", notes = "해당 페이지의 게시글을 호출한다")
	@GetMapping("/list/{pageNum}")
	ResponseEntity<?> getPagingList(@PathVariable int pageNum){
		return new ResponseEntity<Page<BoardResponseDto>>(boardService.getPagingList(pageNum), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글보기(키워드)", notes = "키워드에 해당하는 게시글의 정보를 반환한다.")
	@GetMapping("/search/{keyword}")
	public ResponseEntity<?> getArticle(@PathVariable("keyword") String keyword) throws Exception {
		return new ResponseEntity<List<BoardResponseDto>>(boardService.findAll(keyword), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.")
	@GetMapping("/{id}")
	ResponseEntity<?> findById(@PathVariable Long id){
		return new ResponseEntity<BoardResponseDto>(boardService.findById(id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글수정", notes = "새로운 게시글 정보를 입력한다.")
	//얘 기존에는 requestDto로 한번에 받아왔는데 이제 id는 path로 내려옴
	@PutMapping("/{id}")
	ResponseEntity<?> updateNotice(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
		return new ResponseEntity<Long>(boardService.updateNotice(id, requestDto), HttpStatus.OK);
	}
	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다.")
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteNotice(@PathVariable Long id){
		return new ResponseEntity<Long>(boardService.deleteNotice(id), HttpStatus.OK);
	}
	
}
