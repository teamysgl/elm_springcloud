drop table if exists `coupon`;
Create table coupon(

couponId int,
userId int(11),
limitNum int,
minusNum int,
couponDate Varchar(50),
outDate Varchar(50),
primary key(couponId)

);