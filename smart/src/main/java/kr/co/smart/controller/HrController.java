package kr.co.smart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.smart.hr.DepartmentVO;
import kr.co.smart.hr.EmployeeVO;
import kr.co.smart.hr.HrMapper;
import lombok.AllArgsConstructor;

@Controller @RequestMapping("/hr") @AllArgsConstructor
public class HrController {
	private HrMapper mapper;
	
	//선택한 사원 정보수정화면 요청
	@RequestMapping("/modify")
	public String modify(int id, Model model) {
		//선택한 사원정보를 DB에서 조회해오기
		EmployeeVO vo = mapper.getOneEmployee(id);
		//-> 수정화면에 출력할 수 있도록 Model객체에 저장하기
		model.addAttribute("vo", vo);
		return "hr/modify";
	}
	
	//선택한 사원 정보화면 요청
	@RequestMapping("/info")
	public String info(int id, Model model) {
		//선택한 사원정보를 DB에서 조회해오기
		EmployeeVO vo = mapper.getOneEmployee(id);
		//-> 화면에 출력할 수 있도록 Model객체에 저장하기
		model.addAttribute("vo", vo);
		return "hr/info";
	}

	//사원목록화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session, Model model
						,  @RequestParam(defaultValue = "-1") int department_id) {
		session.setAttribute("category", "hr");
		
		//선택한 부서코드 화면에서 사용할 수 있게
		model.addAttribute("department_id", department_id); 
		
		//부서목록을 DB에서 조회해오기
		List<DepartmentVO> departments = mapper.getListOfDepartmentWithEmployee();
		model.addAttribute("departments", departments);
		
		//사원목록을 DB에서 조회해오기
		List<EmployeeVO> list = mapper.getListOfEmployee(department_id);
		//-> 사원목록화면에 출력할 수 있도록 Model객체에 저장하기
		model.addAttribute("list", list);
		return "hr/list";
	}
}








