CREATE TABLE Wallet (
     walletId INT PRIMARY KEY AUTO_INCREMENT,
     userId VARCHAR(20),
     balance DOUBLE,
     transactionNum DOUBLE,
     transactionTime DATETIME,
     transactionType VARCHAR(50),
     FOREIGN KEY (userId) REFERENCES user(userId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;