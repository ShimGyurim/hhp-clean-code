delete from sugang_history;
delete from sugang_schedule;
delete from sugang;
delete from student;


insert into student(student_id)
select 1 from dual union
select 2 from dual union
select 3 from dual union
select 4 from dual union
select 4 from dual union
select 5 from dual union
select 6 from dual union
select 7 from dual union
select 8 from dual union
select 9 from dual union
select 10 from dual union
select 11 from dual union
select 12 from dual union
select 13 from dual union
select 14 from dual union
select 15 from dual union
select 16 from dual union
select 17 from dual union
select 18 from dual union
select 19 from dual union
select 20 from dual union
select 21 from dual union
select 22 from dual union
select 23 from dual union
select 24 from dual union
select 20 from dual union
select 21 from dual union
select 22 from dual union
select 23 from dual union
select 24 from dual union
select 25 from dual union
select 26 from dual union
select 27 from dual union
select 28 from dual union
select 29 from dual union
select 30 from dual union
select 31 from dual union
select 32 from dual union
select 33 from dual union
select 34 from dual union
select 35 from dual union
select 36 from dual union
select 37 from dual union
select 38 from dual union
select 39 from dual union
select 40 from dual ;




insert into sugang(sugang_id,class_name,teacher)
select 1 , 'a' ,'kim' from dual union
select 2 , 'b' , 'lee' from dual union
select 3 , 'c' , 'choi' from dual union
select 4 , 'd' , 'park' from dual union
select 5 , 'e' , 'jeong' from dual;


insert into SUGANG_SCHEDULE (SUGANGSCHEDULE_ID , avail_num, classdate, sugang_id)
select 1 , 30, '20240801',1from dual union
select 2 , 30, '20240802',2 from dual union
select 3 , 30, '20240803',3 from dual union
select 4, 30, '20240804',4 from dual union
select 5 , 30, '20240805',5 from dual;
