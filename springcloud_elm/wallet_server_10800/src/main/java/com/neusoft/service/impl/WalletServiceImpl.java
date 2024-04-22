package com.neusoft.service.impl;

import com.neusoft.mapper.UserMapper;
import com.neusoft.mapper.WalletMapper;
import com.neusoft.po.TransactionType;
import com.neusoft.po.User;
import com.neusoft.po.Wallet;
import com.neusoft.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {
	@Autowired
	private WalletMapper walletMapper;

	@Autowired
	private UserMapper userMapper0;

	@Override
	public List<Wallet> listWallets(String userId, String token) throws ParseException {
		User user = new User();
		user.setUserId(userId);
		user.setPassword(token);
		if (userMapper0.getUserByIdByPass(user) == null)
			return null;

		List<Wallet> list = new ArrayList<>();
		list = walletMapper.listWalletsByUserId(userId);
		return list;
	}

	@Override
	public int deposit(String userId, double amount) {
		try {
			// 获取用户当前余额
			double balance = getWalletBalance(userId);
			balance = balance += amount;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 创建 Wallet 对象
			Wallet wallet = new Wallet(null, userId, balance, amount, sdf.format(new Date()), TransactionType.DEPOSIT);

			// 调用 Mapper 方法插入数据
			walletMapper.addWalletByWallet(wallet);

			// 插入成功，返回 1
			return 1;
		} catch (Exception e) {
			// 插入失败，记录异常并返回 0
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int withdraw(String userId, double amount) {
		try {
	        // 获取用户当前余额
	        double balance = getWalletBalance(userId);

	        // 如果余额不足，直接返回 0
	        if (balance < amount) {
	            return 0;
	        }

	        // 更新余额
	        balance -= amount;

	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        // 创建 Wallet 对象并插入数据
	        Wallet wallet = new Wallet(null, userId, balance, amount, sdf.format(new Date()), TransactionType.WITHDRAWAL);
	        walletMapper.addWalletByWallet(wallet);

	        // 插入成功，返回 1
	        return 1;
	    } catch (Exception e) {
	        // 记录异常并返回 0
	        e.printStackTrace();
	        return 0;
	    }
	}

	@Override
	public int expense(String userId, double amount) {
	    try {
	        // 获取用户当前余额
	        double balance = getWalletBalance(userId);

	        // 如果余额不足，直接返回 0
	        if (balance < amount) {
	            return 0;
	        }

	        // 更新余额
	        balance -= amount;

	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        // 创建 Wallet 对象并插入数据
	        Wallet wallet = new Wallet(null, userId, balance, amount, sdf.format(new Date()), TransactionType.EXPENSE);
	        walletMapper.addWalletByWallet(wallet);

	        // 插入成功，返回 1
	        return 1;
	    } catch (Exception e) {
	        // 记录异常并返回 0
	        e.printStackTrace();
	        return 0;
	    }
	}


	@Override
	public double getWalletBalance(String userId) {
		try {
			// 查询用户所有钱包记录
			List<Wallet> wallets = walletMapper.listWalletsByUserId(userId);

			// 如果用户没有钱包记录，则余额为0
			double balance = 0.0;

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (wallets.isEmpty()) {
				// 创建 Wallet 对象
				Wallet wallet = new Wallet(null, userId, balance, 0.0, sdf.format(new Date()), TransactionType.CREATE);
				// 调用 Mapper 方法插入数据
				walletMapper.addWalletByWallet(wallet);
			}
			
			if (!wallets.isEmpty()) {
				// 找到具有最大钱包ID的记录
				Wallet maxWallet = Collections.max(wallets, Comparator.comparing(w -> w.getWalletId()));

				// 获取最大钱包的余额
				balance = maxWallet.getBalance();
			}

			return balance;
		} catch (Exception e) {
			// 发生异常时记录并返回0
			e.printStackTrace();
			return 0.0;
		}
	}
}
