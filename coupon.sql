drop table if exists `coupon`;
CREATE TABLE coupon (
    couponId INT AUTO_INCREMENT PRIMARY KEY,
    userId VARCHAR(11),
    limitNum INT,
    minusNum INT,
    couponDate VARCHAR(50),
    outDate VARCHAR(50)
);