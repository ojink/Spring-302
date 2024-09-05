package kr.co.smart.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import kr.co.smart.common.CommonUtility;
import lombok.RequiredArgsConstructor;

@Component @RequiredArgsConstructor
public class LoginSuccess implements AuthenticationSuccessHandler {
	private final CommonUtility common;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//세션에 로그인정보 담기
		//Principal: 접근주체자인 사용자
		HttpSession session = request.getSession();
		LoginUser user = (LoginUser)authentication.getPrincipal();
		session.setAttribute("loginInfo", user.getUser() );
		
		//웰컴페이지로 연결
		response.sendRedirect( common.appURL(request) );
	}

}
