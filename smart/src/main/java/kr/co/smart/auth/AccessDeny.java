package kr.co.smart.auth;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AccessDeny implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		//인증은 완료되었으나, 해당요청에 접근할 권한이 없는 경우 발생
		//등록(notice/register, notice/reply/register, board/register -> 목록, 수정/삭제 -> 정보 
		StringBuffer url = new StringBuffer( 
				request.getRequestURI() .replace("reply/register", "list")
										.replace("register", "list")
										.replace("modify", "info")
										.replace("delete", "info") )
				;  
		url.append("?");
		Set<String> keys = request.getParameterMap().keySet();
		for(String key :  keys ) {
			url.append(key).append("=").append( request.getParameter(key) ).append("&");
		}
		url.deleteCharAt( url.length()-1 ); //마지막& 삭제
		
		response.sendRedirect( url.toString() );
	}

}
