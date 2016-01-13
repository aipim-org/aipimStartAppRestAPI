use smartcarth;
drop table products;
create table products(
   uid int(11) primary key auto_increment,
   url varchar(200) null,
   label varchar(200) not null default 'rótulo da etiqueta',
   last_price double not null default 99999.99,
   price double not null default 99999.99,
   likes int(11) not null default 0,
   name varchar(50) not null default 'nome do produto',
   brand varchar(50) not null default 'marca',
   type_description varchar(50) null,
   subtype_description varchar(50) null,
   packing_type varchar(20) not null default 'unidade',
   packing_capacity int(11) not null default 1,
   packing_measure varchar(20) not null default 'UNIDADE',
   iq_code varchar(100) null,
   aisle_label varchar(50) not null default 'categoria',
   aisle int(11) not null default -1,
   aisle_order int(11) not null default -1,
   stock int(11) not null default 0,
   status varchar(20) not null default 'DISABLE',
   owner_uid int(11) not null default 0,
   created_at datetime not null,
   updated_at datetime null
) DEFAULT CHARSET=utf8;