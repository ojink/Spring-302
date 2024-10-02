--방명록 관리
drop table board;
create table board (
id        number constraint board_id_pk primary key,
title     varchar2(300) not null,
content   varchar2(4000) not null,
writer    varchar2(50) not null constraint board_writer_fk 
                         references member(userid) on delete cascade,
writedate date default sysdate not null,
readcnt   number default 0
);

create sequence seq_board start with 1 increment by 1 nocache;

create or replace trigger trg_board 
    before insert on board
    for each row
begin
    select seq_board.nextval into :new.id from dual;
end;
/


insert into board(id, title, content, writer)
values (seq_board.nextval, '테스트 방명록글1', '방명록글 테스트 ', 'test004');

insert into board(title, content, writer)
values ( '테스트 방명록글2', '방명록글 테스트 ', 'test004');
commit;

select * from board;

--방명록 첨부파일 관리
create table board_file (
id            number constraint board_file_id_pk primary key,
filename      varchar2(300) not null,
filepath      varchar2(300) not null,
board_id      number not null constraint board_file_fk 
                                    references board(id) on delete cascade
);

create sequence seq_board_file start with 1 increment by 1 nocache;

create or replace trigger trg_board_file 
    before insert on board_file
    for each row
begin
    select seq_board_file.nextval into :new.id from dual;
end;
/


insert into board (title, content, writer)
select title, content, writer from board;
commit;

select * 
from (  select row_number() over(order by id) no, name, b.* 
  		from board b inner join member on writer = userid )
--where no between #{beginList} and #{endList}
order by id desc
;

-- 첨부파일조회
select * from board where id = 387;
select * from board_file where board_id=387;

select name, b.*, f.id file_id,  filename, filepath
from board b inner join member m on writer = userid  
left outer join board_file f on b.id = f.board_id
where b.id = 387;


--방명록 첨부파일 갯수 조회 함수
create or replace function fn_boardFileCount( b_id number ) 
return number is
cnt  number;
begin
    select count(*) into cnt from board_file where board_id = b_id;    
return cnt;
end;
/


select fn_boardFileCount(387) from dual;



-- 방명록 댓글관리
create table board_comment (
id            number constraint board_comment_id_pk primary key,
content       varchar2(4000) not null,
writer        varchar2(50) constraint board_comment_writer_fk 
                           references member(userid) on delete cascade,
writedate     date default sysdate,
board_id      number constraint board_comment_id_fk 
                            references board(id) on delete cascade,
notify        number default 0 /*0:미확인, 1:확인*/
);

create sequence seq_board_comment start with 1 increment by 1 nocache;

create or replace trigger trg_board_comment
    before insert on board_comment
    for each row
begin
    select seq_board_comment.nextval into :new.id from dual;
end;
/




desc board_comment;
select * from board_comment;

--댓글 여러개로 만들기
insert into board_comment (content,writer,board_id)
select content,writer,board_id from board_comment;
commit;

select userid, name from member;




