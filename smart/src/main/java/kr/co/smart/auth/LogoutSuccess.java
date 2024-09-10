package kr.co.smart.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import kr.co.smart.common.CommonUtility;
import lombok.RequiredArgsConstructor;

@Component @RequiredArgsConstructor
public class LogoutSuccess implements LogoutSuccessHandler{
	private final CommonUtility common;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		//로그인유지 해제 + DB 정보 삭제
		response.sendRedirect( common.appURL(request) ); //웰컴페이지로 연결
	}

}
