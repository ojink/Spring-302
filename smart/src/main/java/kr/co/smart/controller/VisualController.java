package kr.co.smart.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.smart.visual.VisualMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// @Controller + @ResponseBody = @RestController 
@RestController
@RequestMapping("/visual")
public class VisualController {
	private final VisualMapper mapper;

	//년도별 채용인원수 조회 요청
	@RequestMapping("/hirement/year")
	public Object hirementYear() {
		return mapper.getCountHirementByYear();
	}
	
	//월별 채용인원수 조회 요청
	@RequestMapping("/hirement/month")
	public Object hirementMonth() {
		return mapper.getCountHirementByMonth();
	}
	
	//부서별 사원수 조회 요청
	@RequestMapping("/department")
	public Object department() {
		return mapper.getCountByDepartment();
	}
	
//	//시각화 화면 요청
//	@RequestMapping("/list")
//	public String list(HttpSession session) {
//		session.setAttribute("category", "vi");
//		return "visual/list";
//	}
}
