package kr.co.smart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.smart.member.MemberMapper;
import kr.co.smart.member.MemberVO;
import lombok.RequiredArgsConstructor;

@Controller @RequiredArgsConstructor
public class SmartController {
	private final MemberMapper memberMapper;
	private final PasswordEncoder password;	
	
	@RequestMapping("/")
	public String layout(HttpSession session) {
		//임시 로그인해두기- 나중에 삭제/주석 --------------------
		String userid = "test001";
		String userpw = "Test001";
		//화면에서 입력한 아이디/비번이 일치하는 회원정보 조회하기
		MemberVO vo = memberMapper.getOneMember(userid);
		boolean match = false;
		if( vo != null ) {
			//해당 아이디의 회원정보가 있는 경우만 입력비번과 DB의 암호화된 비번의 일치여부 확인
			match = password.matches(userpw, vo.getUserpw());
			if( match ) {
				session.setAttribute("loginInfo", vo);
			}
		}
		//-------------------------------------------------
	
		
		session.removeAttribute("category");
		return "index";
	}
}
