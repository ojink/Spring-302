package kr.co.smart.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smart.common.CommonUtility;
import kr.co.smart.member.MemberMapper;
import kr.co.smart.member.MemberVO;
import lombok.AllArgsConstructor;

@Controller @AllArgsConstructor  @RequestMapping("/member")
public class MemberController {
	private CommonUtility common; 
	private MemberMapper mapper;
	private PasswordEncoder password;

	
	//새비밀번호로 변경저장 처리 요청
	@ResponseBody @RequestMapping("/user/resetPassword")
	public boolean resetPassword(MemberVO vo, String userpw) {
		//MemberVO의 id: park, pw: Abcd1		
		vo.setUserpw( password.encode(userpw) ); //입력비번을 암호화하기
		return mapper.updatePassword(vo)==1 ? true : false;
	}
	
	
	//현재 입력비번이 정확한지 확인 요청
	@ResponseBody
	@RequestMapping("/user/correctPassword")
	public boolean correctPassword(String userid, String userpw) {
		//입력한 비번이 DB의 비번과 일치하는지
		MemberVO vo = mapper.getOneMember(userid);
		return password.matches(userpw, vo.getUserpw());
	}
	
	//비밀번호 변경 화면 요청
	@RequestMapping("/user/changePassword")
	public String changePassword(HttpSession session) {
		session.setAttribute("category", "change");
		return "member/change";
	}
	
	//임시 비밀번호 발급처리 요청
	@ResponseBody @RequestMapping("/tempPassword")
	public String tempPassword(MemberVO vo) {
		//화면에서 입력한 아이디와 이메일이 일치하는 회원에게
		vo = mapper.getOneMemberByUseridAndEmail(vo);
		StringBuffer msg = new StringBuffer("<script>");
		if( vo == null ) {
			msg.append("alert('아이디나 이메일이 맞지 않습니다. \\n확인하세요!'); ");
			msg.append("location='findPassword' ");
		}else {
		//임시비번을 생성한 후 회원정보에 변경저장, 임시버번을 이메일로 알려주기
			String pw = UUID.randomUUID().toString().substring(0, 6); //afgreq <- afgreq-a243aad-fa431-34ffa-d412413
			vo.setUserpw( password.encode(pw)  ) ; //임시비번을 암호화하여 담기
			
			//DB에 변경저장하기
			if( mapper.updatePassword(vo)==1 && common.emailForPassword(vo, pw) ) {
				//발급한 임시비번을 메일로 보내기 -> 임시비번을 사용해 로그인하도록 로그인화면 연결
				msg.append("alert('임시 비밀번호가 발급되었습니다. \\n이메일을 확인하세요'); ");
				msg.append("location='login' ");
				
			}else {
				msg.append("alert('임시 비밀번호 발급 실패ㅠㅠ'); ");
				msg.append("location='findPassword' ");
			}
			
		}
		msg.append("</script>");
		return msg.toString();
	}
	
	//비밀번호찾기 화면 요청
	@RequestMapping("/findPassword")
	public String findPassword(HttpSession session) {
		session.setAttribute("category", "find");
		return "default/member/find";
	}
	
	
	//로그아웃 처리 요청
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		//세션의 로그인정보를 삭제하기
		session.removeAttribute("loginInfo");
		//응답화면-웰컴화면
		return "redirect:/";
	}
	
	
	//로그인 처리 요청
	@ResponseBody @RequestMapping("/smartLogin")
	public String login(String userid, String userpw
						, HttpServletRequest request, HttpSession session) {
		//화면에서 입력한 아이디/비번이 일치하는 회원정보 조회하기
		MemberVO vo = mapper.getOneMember(userid);
		boolean match = false;
		if( vo != null ) {
			//해당 아이디의 회원정보가 있는 경우만 입력비번과 DB의 암호화된 비번의 일치여부 확인
			match = password.matches(userpw, vo.getUserpw());
		}
		
		StringBuffer msg = new StringBuffer("<script>");
		if( match ) {
			//로그인된 경우 - 웰컴페이지로 연결
			//return "redirect:/";
			msg.append("location='").append( common.appURL(request) ) .append("' ");
			//로그인정보를 session에 저장하기
			session.setAttribute("loginInfo", vo);
		}else {
			//로그인되지 않은 경우 - 로그인페이지로 연결
			//return "redirect:login";
			msg.append("alert('아이디나 비밀번호가 일치하지 않습니다'); ");
			msg.append("location='login' ");
		}
		
		msg.append("</script>");
		
		return msg.toString();
	}
	
	//로그인 화면 요청
	@RequestMapping("/login")
	public String login(HttpSession session) {
		session.setAttribute("category", "login");
		return "default/member/login";
	}
}
