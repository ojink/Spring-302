package kr.co.smart.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	//CRUD
	int registerMember(MemberVO vo); 		//회원가입시 회원정보 저장
	List<MemberVO> getListOfMember();		//회원목록 조회-관리자
	MemberVO getOneMember(String userid); 	//회원정보 조회
	int updateMember(MemberVO vo); 			//회원정보 변경저장(마이페이지)
	int deleteMember(String userid);		//회원탈퇴시 삭제
	
	int registerMemberForTest(MemberVO vo);	//테스트용 회원정보 저장
	MemberVO getOneMemberByUseridAndEmail(MemberVO vo); //아이디와 이메일이 일치하는 회원정보 조회
	int updatePassword(MemberVO vo);  		//비밀번호 변경저장
	
}
