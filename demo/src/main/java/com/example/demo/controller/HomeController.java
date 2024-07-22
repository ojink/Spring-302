package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	
	//2. ModelAndView 사용
	//주소의 요청: if( uri.equals("/second") {}
	@RequestMapping("/second")
	public ModelAndView second() {
		ModelAndView model = new ModelAndView();
		model.setViewName("home");
		model.addObject("title", "두번째");
		model.addObject("now", new SimpleDateFormat("hh:mm:ss").format(new Date()));
		return model;
	}
	
	//1. Model 사용
	//주소의 요청: if( uri.equals("/first") {}
	@RequestMapping("/first")
	public String first(Model model) {
		//비니지스로직
		//request 영역에 attribute로 데이터 담아두기 -> 응답화면에서 사용
		model.addAttribute("title", "첫번째");
		model.addAttribute("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		//응답화면
		return "home";
	}
	
}
