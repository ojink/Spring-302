package kr.co.smart.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SmartErrorController implements ErrorController{
	@RequestMapping("/error")
	public String error(HttpServletRequest request, Model model) {
		int code = (Integer)request.getAttribute( RequestDispatcher.ERROR_STATUS_CODE );
		
		//404
		//500
		//403
		//405
		//400
		String error = null;
		switch( HttpStatus.valueOf(code) ) {
		case BAD_REQUEST: //400
			error = "잘못된 요청입니다.<br>파라미터의 데이터타입이 맞지 않습니다";
			break;
		case FORBIDDEN: //403:접근금지
			error = "접근권한이 없는 사용자입니다";
			break;
		case METHOD_NOT_ALLOWED: //405: 메소드 요청방식이 맞지 않을떄
			error = "허용되지 않는 요청방식입니다";
			break;
		case INTERNAL_SERVER_ERROR: //500: 서버내부오류
			Throwable exception 
				= (Throwable)request.getAttribute( RequestDispatcher.ERROR_EXCEPTION );
			error = exception.toString();
			break;
		default:
			break;
		}
		model.addAttribute("error", error);
		return "default/error/" + (code==404 ? 404 : "common");
	}

}
