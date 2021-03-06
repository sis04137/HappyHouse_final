package com.ssafy.happyhouse.domain.comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import com.ssafy.happyhouse.domain.BaseTimeEntity;
import com.ssafy.happyhouse.dto.comment.CommentDto;
import com.ssafy.happyhouse.dto.comment.CommentRequestDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.apachecommons.CommonsLog;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;
	
	@Column
	private int member_id;
	
	@Column(columnDefinition = "TEXT")
	private String comment;
	
	//여기 있던 comment_time이 created로 바뀜
	
	@Column(name="board_id")
	private Long bid;
	
	public void update(CommentRequestDto commentDto) {
		if(commentDto.getComment() != null) {
			this.comment = commentDto.getComment();
		}
	}


}
