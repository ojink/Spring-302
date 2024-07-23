package kr.co.smart.customer;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
	// CRUD
	int registerCustomer(CustomerVO vo); 	//신규고객등록
	List<CustomerVO> getListOfCustomer();	//고객목록조회
	CustomerVO getOneCustomer(int id);		//고객정보조회
	int updateCustomer(CustomerVO vo);		//고객정보수정저장
	int deleteCustomer(int id);     		//고객정보삭제
}
