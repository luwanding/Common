drop database if exists yuanxing;
create database yuanxing DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
use yuanxing;

drop table if exists user;
create table user(
userId   varchar(20),
loginName varchar(100),
password varchar(100),
email    varchar(200)
);

insert into user (userId,loginName ,password ,email) values ('1' ,'abc' ,'abc' ,'wojiushixudelin@gmail.com');
insert into user (userId,loginName ,password ,email) values('2' ,'cba' ,'cba' ,'wojiushixudelin@163.com');
