package kr.co.smart.remember;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class RememberVO {
	private String username, series, token;
	private Date last_used;
}
