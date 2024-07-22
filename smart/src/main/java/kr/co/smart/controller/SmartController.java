package kr.co.smart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SmartController {

	@RequestMapping("/layout")
	public String layout() {
		return "layout";
	}
}
