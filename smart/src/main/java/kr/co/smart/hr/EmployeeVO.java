package kr.co.smart.hr;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class EmployeeVO {
	private int employee_id, salary, manager_id, department_id;
	private String last_name, first_name, name, email, phone_number
					, job_id, department_name, job_title, manager_name;
	private Date hire_date;
	private double commission_pct;
}
