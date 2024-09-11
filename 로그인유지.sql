create table remember (
    username varchar(64) not null, /* userid */
    series varchar(64) constraint remember_pk primary key, /* 식별자  */
    token varchar(64) not null, /* 쿠키에 사용할 토큰 */
    last_used timestamp not null /* 최종사용일시*/
);


select * from remember;
--truncate table remember;