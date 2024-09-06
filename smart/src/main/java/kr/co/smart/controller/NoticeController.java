package kr.co.smart.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.co.smart.auth.LoginUser;
import kr.co.smart.common.CommonUtility;
import kr.co.smart.common.PageVO;
import kr.co.smart.notice.NoticeMapper;
import kr.co.smart.notice.NoticeVO;
import lombok.RequiredArgsConstructor;

@Controller @RequestMapping("/notice") @RequiredArgsConstructor
public class NoticeController {
	private final NoticeMapper mapper;
	private final CommonUtility common;

	//조회:Get, 등록:Post, 수정:Put, 삭제:Delete
	
	//공지글 삭제처리 요청
	@DeleteMapping( {"/delete", "/reply/delete"})
	public String delete(int id, PageVO page, HttpServletRequest request) throws Exception{
		String filepath = mapper.getOneNotice(id).getFilepath();
		//해당 공지글정보를 DB에서 삭제 -> 응답화면: 목록
		if( mapper.deleteNotice(id)==1 ) {
			common.fileDelete(filepath, request);
		}
		
		StringBuffer redirect = new StringBuffer("redirect:/notice/list");
		redirect.append("?pageNo=").append( page.getPageNo() )
				.append("&search=").append( page.getSearch() )
				.append("&keyword=").append( URLEncoder.encode(page.getKeyword(), "utf-8") )
				;
		return redirect.toString();
	}
	
	
	
	//공지글 수정저장처리 요청
	@PutMapping( {"/modify", "/reply/modify"})
	public String modify(NoticeVO vo, PageVO page
						, MultipartFile file, HttpServletRequest request) throws Exception {
		
		//원래 공지글정보 조회해오기
		NoticeVO notice = mapper.getOneNotice(vo.getId());
		
		//첨부파일이 없는 경우
		if( file.isEmpty() ) {
			//원래X -> 그대로 -> 처리X
			//원래O -> 그대로: 화면에 파일명O -> DB정보로 수정정보에 담기
			//원래O -> 파일삭제: 화면에 파일명X -> 물리적파일 삭제
			if( !vo.getFilename().isEmpty() ) {
				vo.setFilepath(notice.getFilepath() );
			}
			
		}else {
		//첨부파일이 있는 경우
			//원래X -> 새로첨부 -> DB에 저장되도록 수정정보에 담기
			//원래O -> 바꿔첨부 -> DB에 저장되도록 수정정보에 담기 + 원래 첨부된 물리적파일 삭제
			vo.setFilename(file.getOriginalFilename());
			vo.setFilepath( common.fileUpload("notice", file, request));
		}
		
		//화면에서 입력한 정보로 DB에 변경저장
		if( mapper.updateNotice(vo)==1 ) {
			//원래O -> 파일삭제: 화면에 파일명X -> 물리적파일 삭제
			//원래O -> 바꿔첨부:  원래 첨부된 물리적파일 삭제
			if( notice.getFilename() != null ) {
				if( vo.getFilename().isEmpty() || !file.isEmpty() ) {
					common.fileDelete( notice.getFilepath(), request);
				}
			}
		}
		//응답화면연결 : 정보화면
		StringBuffer redirect = new StringBuffer("redirect:info");
		redirect.append("?id=").append( vo.getId() )
				.append("&pageNo=").append( page.getPageNo() )
				.append("&search=").append( page.getSearch() )
				.append("&keyword=").append( URLEncoder.encode(page.getKeyword(), "utf-8") )
				;
		return redirect.toString();
	}
	
	//공지글 수정화면 요청
	@GetMapping( {"/modify", "/reply/modify"} )
	public String modify(int id, PageVO page, Model model, HttpServletRequest request) {
		//해당 공지글 정보를 DB에서 조회해와 수정화면에 출력할 수 있도록 Model객체에 담기
		model.addAttribute("page", page);
		NoticeVO vo = mapper.getOneNotice(id);
		model.addAttribute("vo", vo);
		common.fileExist(vo.getFilepath(), model, request);
		return "notice/modify";
	}

	
	
	//첨부파일 다운로드 요청
	@RequestMapping("/download")
	public void download(int id, HttpServletRequest request
							, HttpServletResponse response) throws Exception{
		NoticeVO vo = mapper.getOneNotice(id);
		//해당 글에 첨부된 파일을 
		//서버의 물리적영역에서 복사해 클라이언트컴에 다운로드하기
		common.fileDownload(vo.getFilepath(), vo.getFilename(), request, response);
	}
	
	//공지글 원글/답글 정보화면 요청
	@RequestMapping( {"/info", "/reply/info"} )
	public String info(int id, PageVO page, Model model, HttpServletRequest request) {
		mapper.updateReadCount(id); 	//조회수처리
		//해당 공지글정보를 DB에서 조회해오기 -> 정보화면에 출력할 수 있도록 Model객체에 담기
		NoticeVO vo = mapper.getOneNotice(id);
		model.addAttribute("vo", vo);
		model.addAttribute("page", page);
		model.addAttribute("crlf", "\r\n");
		
		//첨부파일의 실제 존재 유무 확인
		common.fileExist(vo.getFilepath(), model, request);
		
		return "notice/info";
	}
	
	
	//답글 저장처리 요청
	@PostMapping("/reply/register")
	public String reply(NoticeVO vo, PageVO page, MultipartFile file
							, @AuthenticationPrincipal LoginUser user
							, HttpServletRequest request) throws Exception{
		vo.setWriter( user.getUsername() ); //인증된 사용자 아이디 를 글쓴이의 아이디로 담기 
		
		//첨부파일 있는 경우
		if( !file.isEmpty() ) {
			vo.setFilename( file.getOriginalFilename() );
			vo.setFilepath( common.fileUpload("notice", file, request));
		}
		//화면에서 입력한 정보로 DB에 답글저장 처리 -> 응답화면:목록
		mapper.registerReply(vo);
		
		StringBuffer redirect = new StringBuffer("redirect:/notice/list");
		redirect.append("?pageNo=").append( page.getPageNo() )
				.append("&search=").append( page.getSearch() )
				.append("&keyword=").append( URLEncoder.encode(page.getKeyword(), "utf-8") )
				;
		return redirect.toString();
	}
	
	
	//답글쓰기 화면 요청
	@RequestMapping("/reply/register")
	public String reply(int id, Model model, PageVO page) {
		//원글정보를 조회해와 답글등록화면에 출력
		model.addAttribute("page", page);
		model.addAttribute("vo", mapper.getOneNotice(id));
		model.addAttribute("crlf", "\r\n");
		return "notice/reply";
	}
	
	
	//공지글 저장처리 요청
	@PostMapping("/register")
	public String register(@AuthenticationPrincipal LoginUser user
							, NoticeVO vo, MultipartFile file, HttpServletRequest request) {
		vo.setWriter( user.getUsername() ); //인증된 사용자 아이디 를 글쓴이의 아이디로 담기 
		
		//첨부파일 있는 경우
		if( !file.isEmpty() ) {
			vo.setFilename( file.getOriginalFilename() );
			vo.setFilepath( common.fileUpload("notice", file, request) );
		}
		
		//화면에서 입력한 정보로 DB에 신규저장처리 -> 응답화면:목록
		mapper.registerNotice(vo);
		return "redirect:list";
	}
	
	//공지글쓰기 화면 요청
	@GetMapping("/register")
	public String register() {
		return "notice/register";
	}
	
	//공지글 목록 화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session, Model model, PageVO page) {
		session.setAttribute("category", "no");
		
		//DB에서 공지글목록을 조회해오기 -> 화면에 출력할 수 있도록 Model객체에 담기
		//List<NoticeVO> list = mapper.getListOfNotice();
		//model.addAttribute("list", list);
		page.setTotalList( mapper.countOfNotice(page) );
		page.setList( mapper.getListOfNotice(page) );
		model.addAttribute("page", page);
		
		return "notice/list";
	}
}
