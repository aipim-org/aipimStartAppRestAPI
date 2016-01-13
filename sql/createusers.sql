use smartcarth;
drop table users;
create table users(
   uid int(11) primary key auto_increment,
   url varchar(200) null,
   unique_id varchar(200) not null unique,
   name varchar(200) not null default 'nome do usuario',
   label varchar(200) not null default 'cliente comum',
   email varchar(200) not null default 'info@windie.com.br',
   alternative_email varchar(200),
   authbind varchar(200) not null default 'NONE',
   encrypted_password varchar(80) not null default 'NONE',
   salt varchar(10) not null default 'NONE',
   role int(11) not null default -1,
   address varchar(200) null,
   stars int(11) not null default 0,
   last_lat double not null default 0.0,
   last_lon double not null default 0.0,
   status varchar(20) default 'DISABLE',
   owner_uid int(11) not null default 0,
   created_at datetime not null,
   updated_at datetime null
) DEFAULT CHARSET=utf8;