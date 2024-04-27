package com.neusoft.po;

public class Coupon {

	public int couponId;
	public String userId;
	public int limitNum;
	public int minusNum;
	public String couponDate;
	public String outDate;
	
	public Coupon()
	{
		
	}
	public Coupon(int couponId,String userId,int limitNum,int minusNum,String couponDate,String outDate)
	{
		this.couponId=couponId;
		this.userId=userId;
		this.limitNum=limitNum;
		this.minusNum=minusNum;
		this.couponDate=couponDate;
		this.outDate=outDate;
	}
	
	
	public int getCouponId()
	{
		return this.couponId;
	}
	public void setCouponId(int couponId)
	{
		this.couponId=couponId;
	}
	
	public String getUserId()
	{
		return this.userId;
	}
	public void setUserId(String userId)
	{
		this.userId=userId;
	}
	
	public int getLimitNum()
	{
		return this.limitNum;
	}
	public void setLimitNum(int limitNum)
	{
		this.limitNum=limitNum;
	}
	
	public int getMinusNum()
	{
		return this.minusNum;
	}
	public void setMinusNum(int minusNum)
	{
		this.minusNum=minusNum;
	}
	
	public String getCouponDate()
	{
		return  this.couponDate;
	}
	public void setCouponDate(String couponDate)
	{
		this.couponDate=couponDate;
	}
	
	public String getOutDate()

	{
		return this.outDate;
	}
	
	public void setOutDate(String outDate)
	{
		this.outDate=outDate;
	}
	
}
