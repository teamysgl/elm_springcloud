CREATE TABLE Wallet (
     walletId INT PRIMARY KEY AUTO_INCREMENT,
     userId VARCHAR(50),
     balance DOUBLE,
     transactionNum DOUBLE,
     transactionTime DATETIME,
     transactionType VARCHAR(50),
     FOREIGN KEY (userId) REFERENCES User(userId) )
