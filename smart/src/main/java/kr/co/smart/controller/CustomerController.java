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

@Controller @RequestMapping("/customer")
public class CustomerController {
	@Autowired private CustomerMapper mapper; //필드주입

	//신규고객등록 처리 요청
	@PostMapping("/register")
	public String register( CustomerVO vo ) {
		//화면에서 입력한 정보로 DB에 신규저장
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
	public String list(HttpSession session, Model model) {
		session.setAttribute("category", "cu");
		//비지니스로직: DB에서 고객목록 조회해오기 -> 화면에 출력할 수 있게 Model객체에 저장하기
		List<CustomerVO> list = mapper.getListOfCustomer();
		model.addAttribute("list", list);
		
		//응답화면
		return "customer/list";
	}
}
