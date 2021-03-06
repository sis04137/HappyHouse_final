package com.ssafy.happyhouse.dto.comment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime comment_time;
	private Long isbn;	//κΈ μμ΄λ
}
