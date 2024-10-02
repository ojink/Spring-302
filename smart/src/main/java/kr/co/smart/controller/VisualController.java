package kr.co.smart.controller;

import java.util.ArrayList;
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
		int begin = Integer.parseInt(map.get("begin").toString());
		int end = Integer.parseInt(map.get("end").toString());
		ArrayList<String> range = new ArrayList<String>();
		for(int year= begin; year<=end; year++) {
			range.add( String.valueOf(year) ); // [ "2002", "2003", 2004", "2005" ]
		}
		map.put("range", String.join(",", range));
		//배열정보를 하나의 문자로 만들기
		//"2002,2003,2004" -> 배열로 만들기 String.split : []
		//[2002,2003,2004] -> 하나의 문자열로 만들기 String.join: "2002,2003,2004"
		
		map.put("list", mapper.getCountHirementByYearOfTop3(map));
		//map.put("labels", new int[] {2002,2003,2004,2005,2006,2007,2008,2024});
		map.put("labels", range);
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
		return mapper.getCountHirementByYear(map);
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
