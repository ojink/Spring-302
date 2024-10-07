package kr.co.smart.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.smart.board.BoardMapper;
import kr.co.smart.board.BoardVO;
import kr.co.smart.common.CommentVO;
import kr.co.smart.common.CommonUtility;
import kr.co.smart.common.FileVO;
import kr.co.smart.common.PageVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller @RequestMapping("/board")
public class BoardController {
	private final BoardMapper mapper;
	private final CommonUtility common;
	
	@PostMapping("/test")
	public String test(int id, PageVO page, Model model) {
//		String rid = "<input type='hidden' name='id' value='" + id + "' >";
//		String rpage = "<input type='hidden' name='id' value='" + id + "' >";
//		
//		StringBuffer redirect = new StringBuffer("<script>");
//		redirect.append("$('<form method=\"post\" action=\"board/info\"></form>')")
//			.append(".appendTo('body').append('<input type=\"hidden\" name=\"id\" value=\").append("\" >')")
//			;
//		redirect.append("</script>");
//		$("form")
//		 .attr("action", "<c:url value='/${url}'/>")
//		 .append(`<input type="hidden" name="id" value="${id}" >`)
//		 .append(`<input type="hidden" name="pageNo" value="${page.pageNo}" >`)
//		 .append(`<input type="hidden" name="search" value="${page.search}" >`)
//		 .append(`<input type="hidden" name="keyword" value="${page.keyword}" >`)
//		 .append(`<input type="hidden" name="listSize" value="${page.listSize}" >`)
//		 .submit()
		 
		model.addAttribute("id", id);
		model.addAttribute("page", page);
		model.addAttribute("url", "board/info");
	return "include/redirect";
	}
	

	
	//댓글삭제처리 요청
	@ResponseBody @DeleteMapping("/comment/delete/{id}")
	public boolean commentDelete(@PathVariable int id) {
		//해당 댓글을 DB 에서 삭제
		return mapper.deleteComment(id)==1 ?true :false;
	}
	
	
	
	//댓글변경저장처리 요청
	@ResponseBody @PutMapping("/comment/modify")
	public Object commentModify(CommentVO vo) {
		//화면에서 입력한 정보로 DB에 변경저장
		HashMap<String, Object> map = new HashMap<String, Object>();
		if( mapper.updateComment(vo)==1 ) {
			map.put("success", true);
			map.put("message", "성공^^");
			map.put("content", vo.getContent());
		}else {
			map.put("success", false);
			map.put("message", "실패ㅠㅠ");
		}
		return map;
	}
	
	
	//댓글목록조회 요청
	@RequestMapping("/comment/list/{id}/{pageNo}")
	public String commentList( @PathVariable int id, Model model, PageVO page ) {
		//DB에서 해당글의 댓글목록을 조회해와 화면에 출력할 수 있게 Model객체에 담기
		//model.addAttribute("list", mapper.getListOfComment(id));
		page.setListSize(5);
		page.setTotalList( mapper.getCountOfComment(id) ); //댓글건수
		page.setList( mapper.getListOfComment(id, page) );
		model.addAttribute("subPage", page);
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "board/comment/list";
	}
	
	//댓글등록 처리 요청
	@ResponseBody @PostMapping("/comment/register")
	public boolean register(CommentVO vo, Authentication auth) {
		vo.setWriter( auth.getName() ); //글쓴이 지정
		return mapper.registerComment(vo)==1 ? true : false;
	}
	
	
	
	//방명록 삭제처리 요청
	//@DeleteMapping("/delete")
	@PostMapping("/delete")
	public String delete(int id, PageVO page, Model model, HttpServletRequest request) throws Exception{
		//해당 방명록글을 DB에서 삭제한 후 목록화면으로 연결
		//방명록 글 삭제시 첨부파일 함께 삭제됨: 물리적파일 삭제를 위해 파일정보 조회해두기
		List<FileVO> list = mapper.getListOfFile(id);
		if( mapper.deleteBoard(id)==1 ) {
			for( FileVO file  : list ) {
				common.fileDelete( file.getFilepath(), request );
			}
		}
		
		model.addAttribute("id", id);
		model.addAttribute("page", page);
		model.addAttribute("url", "board/list");
		return "include/redirect";
		
//		StringBuffer redirect = new StringBuffer("redirect:list");
//		redirect.append("?id=").append( id )
//				.append("&pageNo=").append(page.getPageNo())
//				.append("&search=").append(page.getSearch())
//				.append("&keyword=").append( URLEncoder.encode( page.getKeyword(), "utf-8" ) )
//				.append("&listSize=").append(page.getListSize())
//				;
//		return  redirect.toString();
	}
	
	//방명록 수정저장처리 요청
	@PutMapping("/modify")
	public String modify( BoardVO vo, PageVO page, Model model, String removed
							, MultipartFile[] files, HttpServletRequest request) throws Exception {
		//첨부파일 있는 경우 업로드하기
		vo.setFileList( common.fileUpload("board", files, request) );
		
		//삭제한 파일이 있는 경우: DB에서 삭제하기 전에 조회해두기
		List<FileVO> removedFiles = null;
		if( ! removed.isEmpty() ) {
			removedFiles = mapper.getListOfRemovedFile(removed);
		}
		
		//화면에서 입력정보로  DB에 변경저장 -> 정보화면으로 연결
		if( mapper.updateBoard(vo, removed) != 0 ) { 
			if( removedFiles != null ) { //물리적파일 삭제하기
				for(FileVO file : removedFiles ) {
					common.fileDelete(file.getFilepath(), request);
				}
			}
		}
		
		
		StringBuffer redirect = new StringBuffer("redirect:info");
		redirect.append("?id=").append( vo.getId() )
				.append("&pageNo=").append(page.getPageNo())
				.append("&search=").append(page.getSearch())
				.append("&keyword=").append( URLEncoder.encode( page.getKeyword(), "utf-8" ) )
				.append("&listSize=").append(page.getListSize())
				;
		return  redirect.toString();
		
//		model.addAttribute("id", vo.getId());
//		model.addAttribute("page", page);
//		model.addAttribute("url", "board/info");
//		ModelAndView view = new ModelAndView();
//		view.addObject("id", vo.getId());
//		view.addObject("page", page);
//		view.addObject("url", "board/info");
//		view.setViewName("include/redirect");
//		return view;
//		return "include/redirect";
	}
	
	//방명록 수정화면 요청
	@RequestMapping("/modify")
	public String modify(int id, PageVO page, Model model, HttpServletRequest request) {
		//해당 정보를 DB에서 조죄해와 수정화면에 출력: Model객체에 담기
		BoardVO vo = mapper.getOneBoard(id);
		model.addAttribute("vo", vo);
		model.addAttribute("page", page);
		
		common.fileExist(vo.getFileList(), model, request);
		return "board/modify";
	}
	
	
	//방명록 첨부파일 다운로드 요청
	@RequestMapping("/download")
	public void download(int id, HttpServletRequest request, HttpServletResponse response) throws Exception{
		FileVO vo = mapper.getOneFile(id);
		common.fileDownload(vo.getFilepath(), vo.getFilename(), request, response);
	}
	
	//방명록 정보화면 요청
	@RequestMapping("/info")
	public String info(int id, PageVO page, Model model, HttpServletRequest request) {
		mapper.updateReadCount(id);
		
		//선택한 방명록 글을 DB에서 조회해 정보화면에 출력할 수 있게 Model객체에 담기
		BoardVO vo = mapper.getOneBoard(id);
		model.addAttribute("vo", vo);
		model.addAttribute("page", page);
		model.addAttribute("crlf", "\r\n");
		
		//첨부파일의 물리적 존재여부
		common.fileExist(vo.getFileList(), model, request);
		
		return "board/info";
	}
	
	
	
	//방명록 등록 저장처리 요청
	@PostMapping("/register")
	public String register(BoardVO vo, MultipartFile[] files
							, Authentication auth
							, HttpServletRequest request) {
		//화면에서 입력한 정보로 DB에 신규저장 후 목록화면으로 연결
		vo.setWriter(auth.getName());
		vo.setFileList( common.fileUpload("board", files, request));
		
		mapper.registerBoard(vo);
		
		return "redirect:list";
	}
	
	
	//방명록 등록화면 요청
	@GetMapping("/register")
	public String register() {
		return "board/register";
	}
	
	
	//방명록 목록화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session, Model model, PageVO page) {
		session.setAttribute("category", "bo");
		//DB에서 방명록 목록을 조회해와 화면에 출력할 수 있도록 Model객체에 담기
		page.setTotalList( mapper.getCountOfBoard(page) );
		page.setList( mapper.getListOfBoard(page) );
		model.addAttribute("page", page);
		return "board/list";
	}
	
}
