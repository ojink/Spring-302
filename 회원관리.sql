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
address2   varchar2(100) /*주소*/
);

update member set email = 'ojink2@naver.com';
commit;

select * from member;
--where userid='hong' and userpw='1234';



