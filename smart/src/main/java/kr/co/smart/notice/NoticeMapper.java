package kr.co.smart.notice;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper {
	//CRUD
	int registerNotice(NoticeVO vo); 	//신규공지글등록
	List<NoticeVO> getListOfNotice();	//공지글목록조회
	NoticeVO getOneNotice(int id); 		//공지글정보조회
	int updateNotice(NoticeVO vo);		//공지글정보변경저장
	int deleteNotice(int id); 			//공지글정보삭제
	int updateReadCount(int id); 		//조회수변경
}
