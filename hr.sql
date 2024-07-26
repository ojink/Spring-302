desc employees;
select t.table_name, t.constraint_name, column_name, constraint_type
from user_constraints t left outer join user_cons_columns c 
on  t.constraint_name = c.constraint_name
where t.table_name in ('EMPLOYEES', 'DEPARTMENTS', 'JOB_HISTORY')
order by 1;

alter table job_history drop constraint jhist_emp_fk;

select * from departments;

select employee_id from employees
where employee_id not in (select distinct manager_id from employees where manager_id is not null);

alter table employees drop constraint emp_manager_fk ;
alter table employees add constraint emp_manager_fk foreign key(manager_id) 
                                                    references employees(employee_id) on delete set null;

alter table departments drop constraint dept_mgr_fk;
alter table departments add constraint dept_mgr_fk 
            foreign key(manager_id) references employees(employee_id) on delete set null;

-- 관리자인 102번
select employee_id from employees where manager_id = 102;
-- 102번이 관리하고 있는 사원 103번
select employee_id, manager_id from employees where employee_id = 103;

select count(*) from employees where manager_id = 207;

select * from employees ;-- where manager_id = 201;

select * from departments;

select employees_seq.nextval from dual;

create or replace trigger trg_employees
    before insert on employees
    for each row
begin
    select employees_seq.nextval into :new.employee_id from dual;
end;
/




