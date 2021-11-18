package com.ssafy.happyhouse.service.board;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssafy.happyhouse.domain.board.Board;
import com.ssafy.happyhouse.domain.board.BoardMapper;
import com.ssafy.happyhouse.domain.board.BoardRepository;
import com.ssafy.happyhouse.domain.member.Member;
import com.ssafy.happyhouse.dto.board.BoardDtoMapper;
import com.ssafy.happyhouse.dto.board.BoardRequestDto;
import com.ssafy.happyhouse.dto.board.BoardResponseDto;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository boardRepository;
	@Autowired
	BoardMapper boardMapper;
	
	int perPage = 10;

	@Override
	public List<BoardResponseDto> findAll() {
		List<Board> list = boardRepository.findAll();
		return BoardDtoMapper.INSTANCE.toDtoList(list);
	}
	
	@Override
	public List<BoardResponseDto> findAll(String keyword) {
		List<Board> list = boardRepository.getListFromKeyword(keyword);
		return BoardDtoMapper.INSTANCE.toDtoList(list);
	} 
	
	@Override
	public BoardResponseDto findById(Long id) {
		Board board = boardRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지입니다." + id));
		int newHit = board.getHit() + 1;
		board.setHit(newHit);
		
		return BoardDtoMapper.INSTANCE.toDto(board);
	}

	@Transactional
	@Override
	public Long saveNotice(BoardRequestDto requestDto) {
		Long id = boardRepository.save(BoardDtoMapper.INSTANCE.toEntity(requestDto)).getId();
		return id;
	}

	@Transactional
	@Override
	public Long updateNotice(Long id, BoardRequestDto requestDto) {
		Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지입니다." + id));
		board.update(requestDto);
		return id;
	}

	@Override
	public Long deleteNotice(Long id) {
		boardRepository.deleteById(id);
		return id;
	}

	@Override
	public Page<BoardResponseDto> getPagingList(int pageNum) {
		PageHelper.startPage(pageNum, perPage);
		Page<BoardResponseDto> page = boardMapper.getPagingList();
		return page;
	}
	
	
	//얘 이제 findById 함수로 흡수되어 있어서 얘는 안 씀
	@Transactional
	@Override
	public int updateHit(Long id) {
		Board post = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지입니다." + id));
		int newHit = post.getHit() + 1;
		post.setHit(newHit);
		return post.getHit();
	}
}
