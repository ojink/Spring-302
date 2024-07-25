package kr.co.smart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.smart.hr.DepartmentVO;
import kr.co.smart.hr.EmployeeVO;
import kr.co.smart.hr.HrMapper;
import kr.co.smart.hr.JobVO;
import lombok.AllArgsConstructor;

@Controller @RequestMapping("/hr") @AllArgsConstructor
public class HrController {
	private HrMapper mapper;
	
	//신규사원등록 저장처리 요청
	@PostMapping("/register")
	public String register(EmployeeVO vo) {
		//화면에서 입력한 정보로  DB에 신규저장하기
		
		//응답화면-목록화면
		return "redirect:list";
	}
	
	
	//신규사원등록 화면 요청
	@GetMapping("/register")
	public String register(Model model) {
		//부서,업무,매니저 선택할 수 있도록 정보 조회해오기
		List<DepartmentVO> departments = mapper.getListOfDepartment();
		model.addAttribute("departments", departments);
		model.addAttribute("jobs", mapper.getListOfJob() );
		model.addAttribute("managers", mapper.getListOfManager());
		
		//응답화면
		return "hr/register";
	}
	
	//매니저인지 확인
	@ResponseBody @RequestMapping("/isManager")
	public int isManager(int id) {
		//화면에서 전달한 사번의 사원이 매니저인지 DB에서 확인하기
		int count = mapper.countIsManager(id) 
					+  mapper.countIsManagerOfDepartment(id);
		return count;
	}
	
	//선택한 사원정보 삭제처리 요청
	@RequestMapping("/delete")
	public String delete(int id) {
		//선택한 사원정보를 DB에서 삭제하기
		mapper.deleteEmployee(id);
		//응답화면 - 목록화면
		return "redirect:list";
	}
	
	
	//선택한 사원정보 수정저장 처리 요청
	@RequestMapping("/update")
	public String update(EmployeeVO vo) {
		//화면에서 변경입력한 정보로 DB에 변경저장하기
		mapper.updateEmployee(vo);
		//응답화면 - 정보화면
		return "redirect:info?id=" + vo.getEmployee_id();
	}
	
	
	//선택한 사원 정보수정화면 요청
	@RequestMapping("/modify")
	public String modify(int id, Model model) {
		//부서목록,업무목록,매니저목록 조회해오기
		List<DepartmentVO> departments = mapper.getListOfDepartment();
		List<JobVO> jobs = mapper.getListOfJob();
		List<EmployeeVO> managers = mapper.getListOfManager(id);
		model.addAttribute("departments", departments);
		model.addAttribute("jobs", jobs);
		model.addAttribute("managers", managers);
		
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








