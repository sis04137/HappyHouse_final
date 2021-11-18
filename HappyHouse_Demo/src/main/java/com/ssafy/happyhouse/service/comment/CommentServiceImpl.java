package com.ssafy.happyhouse.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.domain.board.Board;
import com.ssafy.happyhouse.domain.comment.Comment;
import com.ssafy.happyhouse.domain.comment.CommentRepository;
import com.ssafy.happyhouse.dto.comment.CommentDto;
import com.ssafy.happyhouse.dto.comment.CommentDtoMapper;
import com.ssafy.happyhouse.dto.comment.CommentRequestDto;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;

	@Transactional
	@Override
	public List<CommentDto> list(Long board_id) {
		List<Comment> list = commentRepository.findAllByBid(board_id);
		return CommentDtoMapper.INSTANCE.toDto(list);
	}

	@Transactional
	@Override
	public Long create(CommentRequestDto commentDto) {
//		return commentRepository.create(commentDto) == 1;
		Comment comment = CommentDtoMapper.INSTANCE.toEntityRequest(commentDto);
		return commentRepository.save(comment).getId();
	}

	@Transactional
	@Override
	public Long modify(CommentRequestDto commentDto) {
		
		Comment comment = commentRepository.findById(commentDto.getComment_no())
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
