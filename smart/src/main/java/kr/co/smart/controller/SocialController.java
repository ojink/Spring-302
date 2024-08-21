package kr.co.smart.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.smart.common.CommonUtility;
import kr.co.smart.member.MemberMapper;
import kr.co.smart.member.MemberVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller @RequestMapping("/member")
public class SocialController {
	private final CommonUtility common;
	private final MemberMapper member;
	
	@Value("${naver.client-id}") private String naverId;
	@Value("${naver.client-secret}") private String naverSecret;
	
	//네이버로그인처리 요청
	@RequestMapping("/naverLogin")
	public String naverLogin(HttpSession session, HttpServletRequest request) {
		//네이버 로그인 연동 URL 생성하기
		//https://nid.naver.com/oauth2.0/authorize?response_type=code
		//&client_id=CLIENT_ID
		//&state=STATE_STRING
		//&redirect_uri=CALLBACK_URL
		//세션상태 토큰으로 사용할 문자열
		String state = UUID.randomUUID().toString();
		session.setAttribute("state", state);
		
		StringBuffer url = new StringBuffer(
				"https://nid.naver.com/oauth2.0/authorize?response_type=code");
		url.append("&client_id=").append(naverId);
		url.append("&state=").append(state);
		url.append("&redirect_uri=").append( common.appURL(request, "/member/naverCallback") );
		return "redirect:" + url.toString();
	}

	//네이버콜백요청
	@RequestMapping("/naverCallback")
	public String naverCallback(String code, String state, HttpSession session) {
		//콜백 실패로 에러발생하거나 상태토큰이 일치하지 않으면 접근토큰을 발급받을수 없다
		String sessionState = (String)session.getAttribute("state");
		if( code == null || ! state.equals(sessionState) ) return "redirect:/";
		
		//접근 토큰 발급 요청
		//https://nid.naver.com/oauth2.0/token?grant_type=authorization_code
		//&client_id=jyvqXeaVOVmV
		//&client_secret=527300A0_COq1_XV33cf
		//&code=EIc5bFrl4RibFls1
		//&state=9kgsGTfH4j7IyAkg  
		StringBuffer url = new StringBuffer(
					"https://nid.naver.com/oauth2.0/token?grant_type=authorization_code");
		url.append("&client_id=").append( naverId );
		url.append("&client_secret=").append( naverSecret );
		url.append("&code=").append( code );
		url.append("&state=").append( state );
		String response = common.requestAPI( url.toString() );
		JSONObject json = new JSONObject(response);
		String token = json.getString("access_token");
		String type = json.getString("token_type");
		
		//접근 토큰을 이용하여 프로필 API 호출하기
		response = common.requestAPI( "https://openapi.naver.com/v1/nid/me", type + " " + token ); // {토큰 타입] {접근 토큰]
		json = new JSONObject( response );
		//API호출 결과코드가 정상(00)인 경우 프로필정보에 접근
		if( json.getString("resultcode").equals("00") ) {
			json = json.getJSONObject("response");
			String id = json.getString("id"); //네이버 아이디마다 고유하게 발급되는 값
			String email = json.getString("email");
			String name = json.getString("nickname");
			String profile = json.getString("profile_image");
			// - F: 여성, M: 남성, U: 확인불가 -> F:여, 나머지:남
			String gender = json.getString("gender").equals("F") ? "여" : "남" ;
			String phone = json.getString("mobile");
			if( name.isEmpty() ) name = json.getString("name");
			
			//네이버 프로필정보를 사용자정보로 관리하도록 MemberVO에 담기
			MemberVO vo = new MemberVO();
			vo.setSocial("N");
			vo.setUserid(id);
			vo.setEmail(email);
			vo.setName(name);
			vo.setProfile(profile);
			vo.setGender(gender);
			vo.setPhone(phone);
			
			//네이버로그인이 처음이면 insert 아니면 update
			if( member.getOneMember(id) == null ) {
				member.registerMember(vo);
			}else {
				member.updateMember(vo);
			}
			
			//세션에 로그인정보 담기
			session.setAttribute("loginInfo", vo);
		}
		
		return "redirect:/";
	}
	
	
	
	
	
	
}
