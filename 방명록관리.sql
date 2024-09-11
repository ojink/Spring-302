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












