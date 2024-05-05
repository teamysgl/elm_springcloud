drop table if exists `coupon`;
Create table coupon(
    couponId INT AUTO_INCREMENT PRIMARY KEY,
    userId VARCHAR(20),
    limitNum int,
    minusNum int,
    couponDate Varchar(50),
    outDate Varchar(50)
);