package kr.co.smart.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import kr.co.smart.common.CommonUtility;
import lombok.RequiredArgsConstructor;

@Component @RequiredArgsConstructor
public class LogoutSuccess implements LogoutSuccessHandler{
	private final CommonUtility common;
	@Value("${kakao.client-id}") private String kakaoId;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response
								, Authentication auth)
			throws IOException, ServletException {
		
		String redirect = null;
		//소셜(카카오)로그인시 카카오계정도 함께 로그아웃 되도록 하기
		if( "K".equals( ((LoginUser)auth.getPrincipal()).getUser().getSocial()) ) {
			StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/logout");
			url.append("?client_id=").append(kakaoId);
			url.append("&logout_redirect_uri=").append( common.appURL(request) );
			redirect =  url.toString();
		}else
			redirect = common.appURL(request);
		response.sendRedirect( redirect ); //웰컴페이지로 연결
	}

}
