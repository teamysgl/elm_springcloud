package com.neusoft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.neusoft.po.Coupon;

@Mapper
public interface CouponMapper {

	@Select("select * from coupon where couponId=#{couponId}")
	public Coupon getCouponByCouponId(Coupon coupon);
	
	@Select("select * from coupon where userId=#{userId}")
	public List<Coupon> listCouponByUserId(Coupon coupon);
	
	
	@Insert("insert into coupon values(#{couponId},#{userId},#{limitNum},#{minusNum},#{couponDate},#{outDate})")
	@Options(useGeneratedKeys=true,keyProperty="couponId",keyColumn="couponId")
	public int saveCoupon(Coupon coupon);
	
	@Delete("delete from coupon where couponId=#{couponId}")
	public int deleteCouponByCouponId(Coupon coupon);
	
	
}
