package kr.co.smart.notify;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.smart.common.CommentVO;
import kr.co.smart.common.LowerKeyMap;

@Mapper
public interface NotifyMapper {
	//인증된유저의 방명록에 대한 미확인 댓글 건수 조회
	LowerKeyMap countOfUncheckedCommentOfUser( HashMap<String, Object> map);
	
	//미확인댓글 목록 조회 
	List<CommentVO> getListOfUncheckedComment( String userid );
	//미확인댓글 읽음(확인)처리
	int updateUncheckedComment( String userid );
	//방명록 글에 대한 미확인 댓글수 조회
	int countUncheckedCommentOfBoard( HashMap<String, Object> map );
	
}
