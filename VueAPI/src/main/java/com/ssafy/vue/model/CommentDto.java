package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(value = "CommentDto : 도서 상세정보에 작성하는 도서평에 대한 글정보")
public class CommentDto {

	@ApiModelProperty(value = "글번호")
	private int comment_no;
	@ApiModelProperty(value = "작성자아이디")
	private int user_id;
	@ApiModelProperty(value = "댓글")
	private String comment;
	@ApiModelProperty(value = "작성시각")
	private String comment_time;
	@ApiModelProperty(value = "게시글 번호", example = "111-111-1111")
	private int articleno;


}
