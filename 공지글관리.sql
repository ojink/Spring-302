-- 공지글관리
create table notice (
id            number constraint notice_id_pk primary key, /* PK */
title         varchar2(300) not null, /* 제목 */
content       varchar2(4000) not null, /* 내용 */
writer        varchar2(50) constraint notice_writer_fk 
                                references member(userid) on delete set null, /* 글쓴이: 회원의 아이디 */
writedate     date default sysdate, /* 작성일자 */
readcnt       number default 0, /* 조회수 */
filepath      varchar2(300), /* 첨부파일경로 */
filename      varchar2(300), /* 첨부파일명 */
rid           number, /* 원글id */
root          number, /* 최초원글id */
step          number default 0, /* 순서 */
indent        number default 0, /* 들여쓰기 */
constraint notice_rid_fk foreign key(rid) references notice(id) on delete cascade
);

-- 답글관련 컬럼 추가
alter table notice add (
rid           number, /* 원글id */
root          number, /* 최초원글id */
step          number default 0, /* 순서 */
indent        number default 0, /* 들여쓰기 */
constraint notice_rid_fk foreign key(rid) references notice(id) on delete cascade
);

select id, rid, root, step, indent from notice order by id desc;
update notice set root = id;
commit;

create sequence seq_notice start with 1 increment by 1 nocache;

insert into notice (id, title, content, writer)
values ( seq_notice.nextval, '테스트 공지글', '첫번째 테스트 공지글 입니다', 'admin' );

insert into notice ( title, content, writer )
values ( '두번째 테스트 공지글', '두번째 테스트 공지글 입니다', 'manager' );

commit;

create or replace trigger trg_notice
    before insert on notice
    for each row
begin
    select seq_notice.nextval into :new.id from dual;
    
    -- 원글인 경우만 root에 id와 같은 값(currval) 넣기
    if( :new.root is null ) then
        select seq_notice.currval into :new.root from dual;
    else
    -- 답글인 경우 순서를 위한  step변경
        update notice set step = step + 1
        where root = :new.root and step >= :new.step;
    end if;
    
end;
/




select id, title from notice;

select * from 
(select row_number() over(order by id) no, name, n.* 
from notice n left outer join member m on m.userid = n.writer) n 
where no  between 375 and 384
order by no desc;

insert into notice ( title, content, writer )
select title, content, writer from notice;
commit;


select id, filename, filepath from notice order by id desc;

select count(*) from notice 
where title like '%공%';

