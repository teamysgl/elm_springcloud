package com.neusoft.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.mapper.CouponMapper;
import com.neusoft.po.Coupon;
import com.neusoft.service.CouponService;


@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	public CouponMapper couponMapper;
	
public List<Coupon> ListAvailableCoupon(String userId) throws ParseException
{
	
	Coupon coupon=new Coupon();
	coupon.setUserId(userId);
	List<Coupon> couponList =couponMapper.listCouponByUserId(coupon);
	
	return couponList;
}
	
	public List<Coupon> ListOutCoupon(int userId) throws ParseException
	{
		return null;
	}
	
	public int saveCoupon(Coupon coupon)
	{
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String formattedDate = sdf.format(new Date());
		 Date currentDate=new Date();
		 coupon.setCouponDate(formattedDate);
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(currentDate);
		 calendar.add(Calendar.DAY_OF_MONTH, 3);
		 Date threeDaysLater = calendar.getTime();
		 String outDate=sdf.format(threeDaysLater);
		 coupon.setOutDate(outDate);
		couponMapper.saveCoupon(coupon);
		return 1;
	}
	public int deleteCouponByCouponId(Coupon coupon)
	{
		
		couponMapper.deleteCouponByCouponId(coupon);
		return 1;
	}
	
}
