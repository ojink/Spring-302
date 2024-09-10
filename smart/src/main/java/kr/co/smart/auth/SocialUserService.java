package kr.co.smart.auth;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kr.co.smart.common.CommonUtility;
import kr.co.smart.member.MemberMapper;
import kr.co.smart.member.MemberVO;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class SocialUserService extends DefaultOAuth2UserService{
	private final CommonUtility common;
	private final MemberMapper member;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth = super.loadUser(userRequest);
		Map<String, Object > response = oauth.getAttributes();

		//소셜 종류에 따라 사용자정보의 형태가 다름
		//소셜사용자 정보를 우리 DB에 저장해야 함
		String social = userRequest.getClientRegistration().getRegistrationId();
		MemberVO vo = null;
		if( social.equals("naver") ) {
			vo = setNaverUser( new JSONObject(response) );
		}else if( social.equals("kakao") ) {
			vo = setKakaoUser( new JSONObject(response));
		}
		
		//네이버로그인이 처음이면 insert 아니면 update
		if( member.getOneMember(vo.getUserid()) == null ) {
			member.registerMember(vo);
		}else {
			member.updateMember(vo);
		}
		
		return new LoginUser(vo);
	}
	

	//카카오사용자 정보
	private MemberVO setKakaoUser(JSONObject json) {
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
		vo.setRole("USER");
		return vo;
	}
	
	//네이버사용자 정보
	private MemberVO setNaverUser(JSONObject json) {
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
		vo.setRole("USER");
		return vo;
	}
	
}
