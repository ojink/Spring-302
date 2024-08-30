-- 회원관리
create table member (
name       varchar2(50) not null, /*회원명*/
userid     varchar2(50) constraint member_userid_pk primary key, /*아이디*/
userpw     varchar2(300) not null, /*비밀번호*/
gender     varchar2(3) default '남' not null,  /*성별*/
email      varchar2(50) not null, /*이메일*/
profile    varchar2(300), /*프로필이미지*/
birth      date,  /*생년월일*/
phone      varchar2(13), /*전화번호*/
post       varchar2(5), /*우편번호*/ 
address1   varchar2(300), /*주소*/
address2   varchar2(100), /*주소*/
social     varchar2(1),   /* 소셜: N/K/G(네이버/카카오) */
role       varchar2(10) default 'USER'  /* 관리자여부: USER/ADMIN */
);

--관리자여부를 관리할 컬럼 추가
alter table member add ( role       varchar2(10) default 'USER' );
select userid, role from member;

--소셜로그인에 따른 제약조건변경
alter table member modify( userpw null, email null);

--소셜로그인에 따른 소셜종류 관리 컬럼 추가
alter table member add ( social     varchar2(1) );

-- 실제 이메일을 받을 수 있는 메
update member set email = 'ojink2@naver.com';
commit;

select * from member where role = 'ADMIN';
--where userid='hong' and userpw='1234';

select userid, profile from member;

update member set userid = userid || 2024 where length(userid) <5;
commit;

update member set profile = replace(profile, '2024/08/27', '2024/08/27/') 
;

desc member;



