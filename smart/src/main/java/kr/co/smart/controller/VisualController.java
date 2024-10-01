package kr.co.smart.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.smart.visual.VisualMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// @Controller + @ResponseBody = @RestController 
@RestController
@RequestMapping("/visual")
public class VisualController {
	private final VisualMapper mapper;

	//TOP3부서 년도별 채용인원수 조회 요청
	@RequestMapping("/hirement/top3/year")
	public Object hirementTop3Year(@RequestBody HashMap<String, Object> map) {
		map.put("list", mapper.getCountHirementByYearOfTop3());
		map.put("labels", new int[] {2002,2003,2004,2005,2006,2007,2008,2024});
		return map;
	}
	
	//TOP3부서 월별 채용인원수 조회 요청
	@RequestMapping("/hirement/top3/month")
	public Object hirementTop3Month() {
		HashMap<String, Object> map  = new HashMap<String, Object>();
		map.put("list", mapper.getCountHirementByMonthOfTop3());
		map.put("labels", new int[] {1,2,3,4,5,6,7,8,9,10,11,12});
		return map;
	}
	
	
	//년도별 채용인원수 조회 요청
	@RequestMapping("/hirement/year")
	public Object hirementYear(@RequestBody HashMap<String, Object> map) {
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
