package kr.co.smart.hr;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HrMapper {
	//CRUD
	int registerEmployee(EmployeeVO vo); 		//신규사원등록저장
	List<EmployeeVO> getListOfEmployee(); 		//사원목록조회
	EmployeeVO getOneEmployee(int employee_id); //사원정보조회
	int updateEmployee(EmployeeVO vo); 			//사원정보변경저장
	int deleteEmployee(int employee_id);		//사원정보삭제
	
	List<DepartmentVO> getListOfDepartmentWithEmployee();	//사원들이 속해 있는 부서목록
	List<EmployeeVO> getListOfEmployee(int department_id);	//특정부서에 속한 사원목록조회
	
}
