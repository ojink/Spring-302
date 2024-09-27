package kr.co.smart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smart.visual.VisualMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller @RequestMapping("/visual")
public class VisualController {
	private final VisualMapper mapper;

	//부서별 사원수 조회 요청
	@ResponseBody @RequestMapping("/department")
	public Object department() {
		return mapper.getCountByDepartment();
	}
	
	//시각화 화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session) {
		session.setAttribute("category", "vi");
		return "visual/list";
	}
	
}
