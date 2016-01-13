use smartcarth;
drop table carts;
create table carts(
   uid int(11) primary key auto_increment,
   label varchar(200) not null default 'rótulo da lista',
   likes int(11) not null default 0,
   type varchar(20) not null default 'cart',
   price double not null default 99999.99,
   price_save double not null default 99999.99,
   ship_price double not null default 99999.99,
   ship_code varchar(200) null default '',
   status varchar(20) not null default 'DISABLE',
   owner_uid int(11) not null default 0,
   created_at datetime not null,
   updated_at datetime null
) DEFAULT CHARSET=utf8;