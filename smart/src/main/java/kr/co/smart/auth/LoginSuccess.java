package kr.co.smart.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
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
//		LoginUser user = (LoginUser)authentication.getPrincipal();
//		session.setAttribute("loginInfo", user.getUser() );
		
		//이전요청이 있었던 경우 해당 요청으로 연결하기
		String url = null;
		String savedURL = (String)request.getSession().getAttribute("savedURL");
		if( savedURL != null ) {
			url = savedURL;
			request.getSession().removeAttribute("savedURL");
		}else {
			SavedRequest saved 
				= new HttpSessionRequestCache().getRequest(request, response);
			if( saved != null )	url = saved.getRedirectUrl();
		}
			
		//카테고리 선택되게
		if( url != null ) {
			if( url.contains("customer") )  session.setAttribute("category", "cu");
			else if( url.contains("hr") )  session.setAttribute("category", "hr");
			else if( url.contains("notice") )  session.setAttribute("category", "no");
			else if( url.contains("board") )  session.setAttribute("category", "bo");
			else if( url.contains("data") )  session.setAttribute("category", "da");
			else if( url.contains("visual") )  session.setAttribute("category", "vi");
		}
		//웰컴페이지로 연결
		response.sendRedirect( url==null ? common.appURL(request) : url );
	}

}
