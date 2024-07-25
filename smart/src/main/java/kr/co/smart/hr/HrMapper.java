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
	
	List<DepartmentVO> getListOfDepartment(); 	//회사의 전체 부서목록
	List<JobVO> getListOfJob(); 				//회사의 전체 업무목록
	List<EmployeeVO> getListOfManager();		//회사의 전체 사원목록-매니저
	List<EmployeeVO> getListOfManager(int employee_id);	//회사의 전체 사원목록-매니저(자신은제외)
	
	int countIsManager(int employee_id); 			//매니저 사원인지 확인(관리하고 있는 사원이 있는지)
	int countIsManagerOfDepartment(int employee_id);//부서장 사원인지 확인
}












