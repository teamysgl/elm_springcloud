package com.neusoft.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.neusoft.po.Coupon;


public interface CouponService {

	public List<Coupon> ListAvailableCoupon(String userId) throws ParseException;
	
	public List<Coupon> ListOutCoupon(int userId) throws ParseException;
	
	public int saveCoupon(Coupon coupon);
	
	public int deleteCouponByCouponId(Coupon coupon);
}
