package com.ssafy.happyhouse.dto.comment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
	private Long comment_no;
	private Long user_id;
	private String comment;
	private Long isbn;	//글 아이디
}
