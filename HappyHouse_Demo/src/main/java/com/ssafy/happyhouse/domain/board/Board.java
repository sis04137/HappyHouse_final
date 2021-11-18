package com.ssafy.happyhouse.domain.board;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ssafy.happyhouse.domain.BaseTimeEntity;
import com.ssafy.happyhouse.dto.board.BoardRequestDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Board extends BaseTimeEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long id;
	
	@Column
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@Column
	int hit;
	
	@Column
	Long member_id;
	
	@Builder
	public Board(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public void update(BoardRequestDto requestDto) {
		if(requestDto.getSubject() != null) {
			this.title = requestDto.getSubject();
		}
		if(requestDto.getContent() != null) {
			this.content = requestDto.getContent();
		}
	}
	
}
