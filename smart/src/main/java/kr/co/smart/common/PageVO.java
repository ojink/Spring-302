package kr.co.smart.common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageVO {
	private int listSize = 10; //보여질 목록수
	private int pageSize = 10; //보여질 페이지수
	private int pageNo = 1; //현재페이지번호
	private int totalList; //총목록수(DB에서 조회)
	private int totalPage; //총페이지수
	private int beginList, endList;//보여질 목록번호: 끝/시작 목록번호
	private int beginPage, endPage;//보여질 페이지번호: 끝/시작 페이지번호
	private boolean prev, next;	    //이전/다음 존재여부
	private List<Object> list;  	//글목록(공지글,방명록,QnA,...)
	private String search, keyword;	//검색조건, 검색어
	
	public void setTotalList(int totalList) {
		this.totalList = totalList;
		
		//총페이지수: 총목록수 / 보여질 목록수:올림함수
		// 384/10=38...4 -> 39 : 나머지가 있으면 +1
		totalPage = totalList / listSize; // 38
		if( totalList % listSize > 0 )  ++totalPage;
		
		//끝목록번호: 총 목록수 - (페이지번호-1) * 보여질 목록수
		//시작목록번호: 끝 목록번호 - (보여질 목록수-1)
		endList = totalList - (pageNo-1) * listSize;
		beginList = endList - (listSize-1);
		
		//끝페이지번호: 페이지번호/보여질 페이지수 * 보여질 페이지수
		//시작페이지번호: 끝 페이지번호 - (보여질 페이지수-1)
		//17 : 11 ~ 20 
		//17/10=1...7 -> 2: 나머지가 있으면 +1
		endPage = pageNo / pageSize;
		if( pageNo % pageSize > 0 ) ++endPage;
		endPage  *= pageSize;
		beginPage = endPage - (pageSize-1);
		
		if( totalPage < endPage ) endPage = totalPage;
		
		prev = beginPage > 1;
		next = endPage < totalPage;
	}	
		
	
	
	
	
	
	
	
	
}










