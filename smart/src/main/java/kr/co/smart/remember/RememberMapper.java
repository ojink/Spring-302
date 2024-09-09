package kr.co.smart.remember;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RememberMapper {
	int registerRemember(RememberVO vo); 		//로그인유지 정보 저장
	RememberVO getOneRemember( String series ); //로그인유지 정보 조회
	int updateRemember(RememberVO vo);		 	//로그인유지 정보 변경저장
	int deleteRemember( String series );		//로그인유지 정보 삭제
}
