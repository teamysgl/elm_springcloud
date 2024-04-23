package com.neusoft.po;
import java.util.Date;

public class Wallet {
	 private Integer walletId;
	    private String userId;
	    private double balance;
	    private double transactionNum;
	    private String transactionTime;
	    private TransactionType transactionType;
	    
	    public Wallet(Integer walletId,String userId,double balance,double transactionNum,String transactionTime,TransactionType transactionType)
		{
			this.walletId=walletId;
			this.userId=userId;
			this.balance=balance;
			this.transactionNum=transactionNum;
			this.transactionTime=transactionTime;
			this.transactionType=transactionType;
		}

	    public Integer getWalletId() {
	        return walletId;
	    }

	    public void setWalletId(Integer walletId) {
	        this.walletId = walletId;
	    }

	    public String getUserId() {
	        return userId;
	    }

	    public void setUserId(String userId) {
	        this.userId = userId;
	    }

	    public double getBalance() {
	        return balance;
	    }

	    public void setBalance(double balance) {
	        this.balance = balance;
	    }

	    public String getTransactionTime() {
	        return transactionTime;
	    }

	    public void setTransactionTime(String transactionTime) {
	        this.transactionTime = transactionTime;
	    }

	    public TransactionType getTransactionType() {
	        return transactionType;
	    }

	    public void setTransactionType(TransactionType transactionType) {
	        this.transactionType = transactionType;
	    }

		public double getTransactionNum() {
			return transactionNum;
		}

		public void setTransactionNum(double transactionNum) {
			this.transactionNum = transactionNum;
		}


}
