package com.neusoft.service;

import java.text.ParseException;
import java.util.List;

import com.neusoft.po.Wallet;

public interface WalletService {
    public List<Wallet> listWallets(String userId, String token) throws ParseException;

	public int deposit(String userId, double amount);

	public int withdraw(String userId, double amount);
	
	public int expense(String userId, double amount);

	public double getWalletBalance(String userId);
    
}
