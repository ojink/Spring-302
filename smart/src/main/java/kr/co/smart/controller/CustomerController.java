package kr.co.smart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.smart.customer.CustomerMapper;
import kr.co.smart.customer.CustomerVO;
import lombok.AllArgsConstructor;

@Controller @RequestMapping("/customer") @AllArgsConstructor
public class CustomerController {
	//@Autowired private CustomerMapper mapper; //필드주입
//	//생성자주입
//	private CustomerMapper mapper;
//	public CustomerController(CustomerMapper mapper) {
//		this.mapper = mapper;
//	}
	private CustomerMapper mapper;

	//고객정보 삭제처리 요청
	@RequestMapping("/delete")
	public String delete(int id) {
		//DB에서 선택한 고객정보를 삭제
		mapper.deleteCustomer(id);
		//응답화면: 고객목록
		return "redirect:list";
	}
	
	
	//고객정보 수정저장처리 요청
	@PostMapping("/update")
	public String update( CustomerVO vo ) {
		//화면에서 변경입력한 정보로 DB에 변경저장
		mapper.updateCustomer(vo);
		//응답화면: 고객정보
		return "redirect:info?id=" + vo.getId();
	}
	
	//선택한 고객정보 수정화면 요청
	@RequestMapping("/modify")
	public String modify(int id, Model model) {
		//비지니스로직: 선택한 고객정보를 DB에서 조회해오기
		CustomerVO vo = mapper.getOneCustomer(id);
		//           -> 수정화면에 출력할 수 있도록 Model객체에 저장하기
		model.addAttribute("vo", vo);
		return "customer/modify";
	}
	
	//선택한 고객정보 화면 요청
	@RequestMapping("/info")
	public String info(int id, Model model) {
		//비지니스로직: 선택한 고객정보를 DB에서 조회해오기 
		CustomerVO vo = mapper.getOneCustomer(id);
		//           -> 화면에 출력할 수 있도록 Model객체에 저장하기
		model.addAttribute("vo", vo);
		return "customer/info";
	}
	
	
	//신규고객등록 처리 요청
	@PostMapping("/register")
	public String register( CustomerVO vo ) {
		//화면에서 입력한 정보로 DB에 신규저장
		mapper.registerCustomer(vo);
		//응답화면
		return "redirect:list";
	}
	
	//신규고객등록 화면 요청
	@GetMapping("/register")
	public String register() {
		//응답화면
		return "customer/register";
	}
	
	
	//고객목록 화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session, Model model, String name) {
		session.setAttribute("category", "cu");
		//비지니스로직: DB에서 고객목록 조회해오기 -> 화면에 출력할 수 있게 Model객체에 저장하기
		List<CustomerVO> list = mapper.getListOfCustomer( name );
		model.addAttribute("list", list);
		model.addAttribute("name", name);
		
		//응답화면
		return "customer/list";
	}
}
