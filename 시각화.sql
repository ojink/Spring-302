--부서별 사원수 조회
select department_id, count(employee_id) count, nvl(department_name, '소속없음') department_name
from employees e left outer join departments d using(department_id)
group by department_id, department_name;