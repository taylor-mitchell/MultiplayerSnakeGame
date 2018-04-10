---Drop tables
drop table user;


---Create tables
Create table user(
	username varchar(40),
	password varchar(20) NOT NULL
	);


---Create PKs
alter table user
 add constraint user_username_pk primary key(username);


