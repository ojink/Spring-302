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
	@Value("${kakao.client-id}") private String kakaoId;
	
	//로그아웃 처리 요청
	//@RequestMapping("/logout")
	public String logout(HttpSession session, HttpServletRequest request) {
		MemberVO vo = (MemberVO)session.getAttribute("loginInfo");
		//세션의 로그인정보를 삭제하기
		session.removeAttribute("loginInfo");
		
		//curl -v -G GET "https://kauth.kakao.com/oauth/logout
		//?client_id=${YOUR_REST_API_KEY}
		//&logout_redirect_uri=${YOUR_LOGOUT_REDIRECT_URI}"
		//카카오로그인 한 경우만 
		if( "K".equals(vo.getSocial()) ) {
			StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/logout");
			url.append("?client_id=").append(kakaoId);
			url.append("&logout_redirect_uri=").append( common.appURL(request) );
			return "redirect:" + url.toString();
		}else
			//응답화면-웰컴화면
			return "redirect:/";
	}
		
	
	//카카오로그인처리 요청
	@RequestMapping("/kakaoLogin")
	public String kakaoLogin(HttpServletRequest request) {
		//인가 코드 받기
		//https://kauth.kakao.com/oauth/authorize?response_type=code
		//&client_id=${REST_API_KEY}
		//&redirect_uri=${REDIRECT_URI}
		StringBuffer url = new StringBuffer(
				"https://kauth.kakao.com/oauth/authorize?response_type=code");
		url.append("&client_id=").append(kakaoId);
		url.append("&redirect_uri=").append( common.appURL(request, "/member/kakaoCallback") );
		return "redirect:" + url.toString();
	}
	
	@RequestMapping("/kakaoCallback")
	public String kakaoCallback(String code, HttpSession session) {
		if( code == null ) return "redirect:login";  //카카오로그인 실패시 로그인화면으로 연결
		
		//토큰 받기: 인가 코드로 토큰 발급을 요청
//		curl -v -X POST "https://kauth.kakao.com/oauth/token" \
//		 -d "grant_type=authorization_code" \
//		 -d "client_id=${REST_API_KEY}" \
//		 -d "code=${AUTHORIZE_CODE}"
		StringBuffer url = new StringBuffer(
				"https://kauth.kakao.com/oauth/token?grant_type=authorization_code");
		url.append("&client_id=").append(kakaoId);
		url.append("&code=").append(code);
		String response = common.requestAPI( url.toString() );
		JSONObject json = new JSONObject( response );
		String type = json.getString("token_type");
		String token = json.getString("access_token");
		
		//사용자 정보 가져오기
//		curl -v -G GET "https://kapi.kakao.com/v2/user/me" \
//		  -H "Authorization: Bearer ${ACCESS_TOKEN}"
		response = common.requestAPI("https://kapi.kakao.com/v2/user/me", type + " " + token);
		json = new JSONObject( response );
		if( ! json.isEmpty() ) {
			String id = json.get("id").toString();
			//id 를 제외한 사용자 정보가 kakao_account 에 있다
			json = json.getJSONObject("kakao_account");
			String email = common.hasKey(json, "email");	
			String gender = common.hasKey(json, "gender", "female").equals("female") ? "여" : "남" ; //female/male -> 여/남
			String name = common.hasKey(json, "name");
			String phone = common.hasKey(json, "phone_number");
			
			json = json.getJSONObject("profile");
			String profile = common.hasKey(json, "profile_image_url");
			if( name.isEmpty() ) name = common.hasKey(json, "nickname", "무명씨");
			
			//카카오 프로필정보를 사용자정보로 관리하도록 MemberVO에 저장하기
			MemberVO vo = new MemberVO();
			vo.setSocial("K");
			vo.setUserid(id);
			vo.setEmail(email);
			vo.setGender(gender);
			vo.setName(name);
			vo.setPhone(phone);
			vo.setProfile(profile);
			
			//카카오로그인이 처음이면 insert 아니면 update
			if( member.getOneMember(id)==null ) {
				member.registerMember(vo); 
			}else {
				member.updateMember(vo);
			}
			session.setAttribute("loginInfo", vo);
		}
		
		return "redirect:/";   // 카카오로그인 성공시 웰컴화면으로 연결
	}
	
	
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
			String email = common.hasKey(json, "email");
			String name = common.hasKey(json, "nickname");
			String profile = common.hasKey(json, "profile_image");
			// - F: 여성, M: 남성, U: 확인불가 -> F:여, 나머지:남
			String gender = common.hasKey(json, "gender", "M").equals("F") ? "여" : "남" ;
			String phone =  common.hasKey(json, "mobile");
			if( name.isEmpty() ) name = common.hasKey(json, "name", "익명씨");
			
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
