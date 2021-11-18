package com.ssafy.happyhouse.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
	private Long comment_no;
	private Long user_id;
	private String comment;
	private String comment_time;
	private String isbn;
}
