package kr.co.smart.hr;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DepartmentVO {
	private int department_id, location_id, manager_id;
	private String department_name; 
}
