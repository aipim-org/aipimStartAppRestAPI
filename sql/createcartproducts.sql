use smartcarth;
drop table cart_products;
create table cart_products(
   cart_uid int(11) not null default 0,
   product_uid int(11) not null default 0,
   quantity int(11) not null default 0,
   price double not null default 99999.99, /* pre�o efetivado na compra (o produto tem seu pre�o din�mico) */
   price_save double not null default 99999.99, /* pre�o praticado na compra */
   ship_price double not null default 99999.99, /* frete do produto */
   ship_code varchar(200) null default ''
) DEFAULT CHARSET=utf8;