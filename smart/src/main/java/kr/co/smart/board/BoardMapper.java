package kr.co.smart.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.smart.common.CommentVO;
import kr.co.smart.common.FileVO;
import kr.co.smart.common.PageVO;

@Mapper
public interface BoardMapper {
	int registerBoard(BoardVO vo); 				//신규방명록등록
	List<Object> getListOfBoard(PageVO page);	//방명록 목록조회-페이지처리
	int getCountOfBoard(PageVO page); 			//방명록 총 건수조회
	BoardVO getOneBoard(int id); 				//방명록 정보조회
	int updateReadCount(int id);				//방명록 조회수 변경저장
	int updateBoard(BoardVO vo, String removed);//방명록 변경저장
	int deleteBoard(int id);					//방명록 삭제
	
	FileVO getOneFile(int id); 					//첨부파일정보조회
	List<FileVO> getListOfRemovedFile(String removed);  //삭제할 파일정보 조회
	List<FileVO> getListOfFile(int id); 		//방명록에 첨부된 파일목록 조회
	
	int registerComment(CommentVO vo); 			//댓글등록
	int getCountOfComment(int board_id);		//댓글 건수조회
	List<Object> getListOfComment(int board_id, PageVO page);//댓글목록조회
	CommentVO getOneComment(int id);			//댓글정보조회
	int updateComment(CommentVO vo);			//댓글변경저장
	int deleteComment(int id);					//댓글삭제
	
	
	
}
