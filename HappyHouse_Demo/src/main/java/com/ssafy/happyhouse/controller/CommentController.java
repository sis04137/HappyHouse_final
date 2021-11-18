package com.ssafy.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.happyhouse.domain.comment.Comment;
import com.ssafy.happyhouse.service.comment.CommentService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/comment")
public class CommentController {
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	CommentService commentService;

	@ApiOperation(value = "글 번호에 해당하는 댓글 목록을 반환한다.", response = List.class)
	@GetMapping("{articleno}")
	public ResponseEntity<List<Comment>> listComment(@PathVariable("articleno") Long bid) {
		return new ResponseEntity<>(commentService.list(bid), HttpStatus.OK);
	}

	@ApiOperation(value = "새로운 댓글을 입력한다. 댓글 id 반환", response = String.class)
	@PostMapping
	public ResponseEntity<?> createComment(@RequestBody Comment commentDto) {
		return new ResponseEntity<Long>(commentService.create(commentDto), HttpStatus.OK);
	}

	@ApiOperation(value = "해당 객체의 comment_id에 해당하는 도서평을 수정한다. 해당 댓글 id를 반환", response = String.class)
	@PutMapping
	public ResponseEntity<?> modifyComment(@RequestBody Comment commentDto) {
		return new ResponseEntity<Long>(commentService.modify(commentDto), HttpStatus.OK);
	}

	@ApiOperation(value = "해당 댓글 아이디의 댓글 삭제. 댓글 id 재반환. ", response = String.class)
	@DeleteMapping("{commentNO}")
	public ResponseEntity<Long> deleteBook(@PathVariable("commentNO") Long id) {
		return new ResponseEntity<Long>(commentService.delete(id), HttpStatus.OK);
	}
	
}
