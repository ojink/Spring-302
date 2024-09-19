package kr.co.smart.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.smart.common.FileVO;
import kr.co.smart.common.PageVO;

@Mapper
public interface BoardMapper {
	int registerBoard(BoardVO vo); 				//신규방명록등록
	List<Object> getListOfBoard(PageVO page);	//방명록 목록조회-페이지처리
	int getCountOfBoard(PageVO page); 			//방명록 총 건수조회
	BoardVO getOneBoard(int id); 				//방명록 정보조회
	int updateReadCount(int id);				//방명록 조회수 변경저장
	int updateBoard(BoardVO vo);				//방명록 변경저장
	int deleteBoard(int id);					//방명록 삭제
	
	FileVO getOneFile(int id); 					//첨부파일정보조회
}
