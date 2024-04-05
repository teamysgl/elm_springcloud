package com.neusoft.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.po.CommonResult;
import com.neusoft.po.Coupon;
import com.neusoft.service.CouponService;
import com.neusoft.service.impl.CouponServiceImpl;

@RestController
@RequestMapping("/CouponController")
public class CouponController {

	@Autowired
	public CouponServiceImpl couponService;
	
	@PostMapping("/saveCoupon/{userId}/{limitNum}/{minusNum}")
    public CommonResult<Integer> listOutofDatePoint(@PathVariable("userId") String userId,@PathVariable("limitNum") int limitNum,@PathVariable("minusNum") int minusNum) throws Exception {
        
		Coupon coupon =new Coupon();
		coupon.setUserId(userId);
		coupon.setLimitNum(limitNum);
		coupon.setMinusNum(minusNum);
		return new CommonResult<>(200,"success",couponService.saveCoupon(coupon));
    }
	
	@GetMapping("/getCouponByUserId/{userId}")
	public CommonResult<List<Coupon>> getCouponByUserId(@PathVariable String userId) throws ParseException
	{
		return new CommonResult<>(200,"success",couponService.ListAvailableCoupon(userId));
	}
	
	@DeleteMapping("/deleteCouponByCouponId/{couponId}")
	public CommonResult<Integer> deleteCouponByCouponId(@PathVariable("couponId") int couponId)
	{
		Coupon coupon =new Coupon();
		coupon.setCouponId(couponId);
		return new CommonResult<>(200,"success",couponService.deleteCouponByCouponId(coupon));
	}
	
}
