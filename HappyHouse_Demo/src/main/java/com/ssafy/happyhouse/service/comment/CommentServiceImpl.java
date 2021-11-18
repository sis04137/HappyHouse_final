package com.ssafy.happyhouse.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.domain.board.Board;
import com.ssafy.happyhouse.domain.comment.Comment;
import com.ssafy.happyhouse.domain.comment.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;

	@Transactional
	@Override
	public List<Comment> list(Long board_id) {

		return commentRepository.findAllByBid(board_id);
	}

	@Transactional
	@Override
	public Long create(Comment commentDto) {
//		return commentRepository.create(commentDto) == 1;
		return commentRepository.save(commentDto).getId();
	}

	@Transactional
	@Override
	public Long modify(Comment commentDto) {
		Comment comment = commentRepository.findById(commentDto.getId())
				.orElseThrow(() -> new IllegalArgumentException("잘못된 댓글 id입니다."));
		comment.update(commentDto);
		return comment.getId();
	}

	@Transactional
	@Override
	public Long delete(Long id) {
//		return commentRepository.delete(commentNo) == 1;
		commentRepository.deleteById(id);
		return id;
	}
}
