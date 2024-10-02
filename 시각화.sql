--년도별 채용인원수 조회
--2020년 10명
--2021년 5명
select to_char(hire_date, 'yyyy') unit, count(employee_id) count
from employees
group by to_char(hire_date, 'yyyy')
order by unit
;

select to_char(hire_date, 'mm') unit, count(employee_id) count
from employees
group by to_char(hire_date, 'mm')
order by unit
;


--부서별 사원수 조회
select department_id, count(employee_id) count, nvl(department_name, '소속없음') department_name
from employees e left outer join departments d using(department_id)
group by department_id, department_name;



-- 부서원수 상위3위까지의 부서
select rank, department_id, '(TOP' || rank || ')'||department_name department_name
from (select dense_rank() over(order by count(*) desc) rank, department_id
      from employees
      group by department_id) e left outer join departments d using(department_id)
where rank <= 3
;

--상위3위까지의 부서의 월별 채용인원수
select to_char(hire_date, 'mm') unit, count(*) count
from employees
group by to_char(hire_date, 'mm');



select department_name, to_char(hire_date, 'mm') unit 
from employees e inner join 
        (select rank, department_id, '(TOP' || rank || ')'||department_name department_name
        from (select dense_rank() over(order by count(*) desc) rank, department_id
              from employees
              group by department_id) e left outer join departments d using(department_id)
        where rank <= 3) r using(department_id)
order by 1, 2        
;        

--상위3위까지의 부서의 월별 채용인원수 가로행으로
select department_name, extract(month from hire_date) unit 
from employees e inner join 
        (select rank, department_id, '(TOP' || rank || ')'||department_name department_name
        from (select dense_rank() over(order by count(*) desc) rank, department_id
              from employees
              group by department_id) e left outer join departments d using(department_id)
        where rank <= 3) r using(department_id)
--order by 1, 2 
;

select  *
from (select department_name, extract(month from hire_date) unit 
        from employees e inner join 
                (select rank, nvl(e.department_id,0) department_id, '(TOP' || rank || ')'||nvl(department_name,'소속없음') department_name
                from (select dense_rank() over(order by count(*) desc) rank, department_id
                      from employees
                      group by department_id) e left outer join departments d on nvl(e.department_id,0)=d.department_id
                where rank <= 3) r on nvl(e.department_id,0)=r.department_id)
pivot( count(unit) for unit in (1,2,3,4,5,6,7,8,9,10,11,12) )
order by department_name
;

select distinct extract(year from hire_date) from employees
order by 1;

select  *
from (select department_name, extract(year from hire_date) unit 
        from employees e inner join 
                (select rank, nvl(e.department_id,0) department_id, '(TOP' || rank || ')'||nvl(department_name,'소속없음') department_name
                from (select dense_rank() over(order by count(*) desc) rank, department_id
                      from employees
                      group by department_id) e left outer join departments d on nvl(e.department_id,0)=d.department_id
                where rank <= 3) r on nvl(e.department_id,0)=r.department_id)
pivot( count(unit) for unit in (2002,2003,2004,2005,2006,2007,2008,2024) )
order by department_name
;

-- 세로행을 가로행으로: pivot
-- 가로행을 세로행으로: unpivot


-- 각 사원의 부서코드 조회
select nvl(department_id,0) department_id from employees
order by 1;
-- pivot하기(가로행으로 만들기)
select *
from (select nvl(department_id,0) department_id from employees)
pivot( count(department_id) for department_id in(0,10,20,30,40,50,60,70,80,90,100,110) )
;


-- 각 부서별 사원수 조회
select nvl(department_id,0) department_id, count(employee_id) count 
from employees
group by department_id
order by 1;
-- pivot하기(가로행으로 만들기)
select * 
from (select nvl(department_id,0) department_id, count(employee_id) count 
        from employees
        group by department_id) 
pivot( sum(count) for department_id in(0,10,20,30,40,50,60,70,80,90,100,110) )         
;


-- 사원들의 입사월 조회
select to_char(hire_date,'mm'), extract(month from hire_date) mm from employees
order by 1;
-- pivot하기(가로행으로 만들기)
select *
from (select to_char(hire_date,'mm') mm from employees)
pivot( count(mm) for mm in('01','02','03','04','05','06','07','08','09','10','11','12') )
;
select *
from (select extract(month from hire_date) mm from employees)
pivot( count(mm) for mm  in (1,2,3,4,5,6,7,8,9,10,11,12) )
;

-- 입사월별 사원수 조회
select to_char(hire_date,'mm'), count(employee_id) from employees
group by to_char(hire_date,'mm')
order by 1
;
-- pivot하기(가로행으로 만들기)
select *
from (select to_char(hire_date,'mm') mm, count(employee_id) count from employees
      group by to_char(hire_date,'mm') )
pivot( sum(count) for mm in('01','02','03','04','05','06','07','08','09','10','11','12') )
;