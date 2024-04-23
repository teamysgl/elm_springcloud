package com.neusoft.mapper;


import com.neusoft.po.Wallet;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface WalletMapper {
    @Select("select * from wallet where userId=#{userId}")
    public List<Wallet> listWalletsByUserId(String userId);

    @Select("insert into wallet(userId,balance,transactionNum,transactionTime,transactionType) values(#{userId},#{balance},#{transactionNum},#{transactionTime},#{transactionType})")
    @Options(useGeneratedKeys=true,keyProperty="walletId",keyColumn="walletId")
    public void addWalletByWallet(Wallet wallet);
    
}
