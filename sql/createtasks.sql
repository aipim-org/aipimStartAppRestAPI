use smartcarth;
drop table tasks;
create table tasks(
   uid int(11) primary key auto_increment,
   token varchar(200) null,
   staging tinyint(1) default 1,
   message text,
   status varchar(20) not null default 'NOTIFIED',
   request text,
   response text,
   start_time datetime not null,
   end_time datetime null,
   archived tinyint(1) default 0
) DEFAULT CHARSET=utf8;