package kr.co.smart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SmartController {

	
	@RequestMapping("/")
	public String layout(HttpSession session) {
		session.removeAttribute("category");
		return "index";
	}
}
