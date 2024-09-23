package kr.co.smart.common;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentVO {
	private int id, parent_id;
	private String content, writer, name, writedate, profile;
}
