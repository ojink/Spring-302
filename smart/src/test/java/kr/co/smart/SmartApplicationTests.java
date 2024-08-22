package kr.co.smart;

import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.co.smart.customer.CustomerMapper;
import kr.co.smart.customer.CustomerVO;
import kr.co.smart.member.MemberMapper;
import kr.co.smart.member.MemberVO;

@SpringBootTest
class SmartApplicationTests {

	@Autowired private CustomerMapper mapper;	//필드주입
	@Autowired private MemberMapper member;	//필드주입
	@Autowired private PasswordEncoder passwordEncoder;
	
	
	//로그인
	@Test
	void login() {
		Scanner scan = new Scanner(System.in);
		System.out.print("아이디: ");
		String userid = scan.next();
		
		System.out.print("비밀번호: ");
		String userpw = scan.next();
		
		scan.close();
		
		MemberVO vo = member.getOneMember(userid);
		if( vo==null ) {
			System.out.println("아이디 불일치");
		}else {
			//입력비번과 암호화된 DB의 비번의 일치여부 확인
			boolean match = passwordEncoder.matches(userpw, vo.getUserpw());
			if( match ) {
				System.out.println( vo.getName()+ " 회원으로 로그인됨");
			}else {
				System.out.println("비번 불일치");
			}
			
		}
		
	}
	
	@Test
	void updatePassword() {
		Scanner scan = new Scanner(System.in);
		
		MemberVO vo = new MemberVO();
		System.out.print("아이디: ");
		vo.setUserid( scan.next() );
		
		System.out.print("비번: ");
		//입력한 비밀번호를 암호화시켜서 DB에 저장할 수 있도록 암호화하기
		vo.setUserpw(  passwordEncoder.encode( scan.next() ) );
		scan.close();
		member.updatePassword(vo);
		
	}
	//회원가입(회원정보저장)
	@Test
	void join() {
		Scanner scan = new Scanner(System.in);
		
		MemberVO vo = new MemberVO();
		System.out.print("이름: ");
		String name = scan.next();
		vo.setName(name);
		
		System.out.print("아이디: ");
		vo.setUserid( scan.next() );
		
		System.out.print("비번: ");
		//입력한 비밀번호를 암호화시켜서 DB에 저장할 수 있도록 암호화하기
		vo.setUserpw(  passwordEncoder.encode( scan.next() ) );
		
		System.out.print("이메일: ");
		vo.setEmail( scan.next() );
		
		scan.close();
		
		int dml = member.registerMemberForTest(vo);
		System.out.println( dml == 1 ? "가입성공" : "가입실패");
	}
	
	
	@Test
	void customerList() {
		List<CustomerVO> list = mapper.getListOfCustomer();
		System.out.println( "고객수> "+list.size() );
	}

}
