package kr.co.smart.visual;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VisualMapper {
	List<HashMap<String, Object>> getCountByDepartment(); //부서별 사원수
	List<HashMap<String, Object>> getCountHirementByYear(); //년도별 채용인원수
	List<HashMap<String, Object>> getCountHirementByMonth(); //월별 채용인원수
}
