package com.neusoft.mapper;

import com.neusoft.po.DeliveryAddress;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeliveryAddressMapper {
	 @Select("select * from deliveryAddress where userId=#{userId} and deleted=0 order by daId")
	 public List<DeliveryAddress> listDeliveryAddressByUserId(String userId);
	 
	 @Select("select * from deliveryAddress where daId=#{daId}")
	 public DeliveryAddress getDeliveryAddressById(Integer daId);
	 
	 @Insert("insert into deliveryAddress values(null,#{contactName},#{contactSex},#{contactTel},#{address},#{userId},0,0)")
	 public int saveDeliveryAddress(DeliveryAddress deliveryAddress);
	 
	 @Update("update deliveryAddress set contactName=#{contactName},contactSex=#{contactSex},contactTel=#{contactTel},address=#{address},deleted=#{deleted},used=#{used} where daId=#{daId}")
	 public int updateDeliveryAddress(DeliveryAddress deliveryAddress);
	 
	 @Delete("delete from deliveryAddress where daId=#{daId}")
	 public int removeDeliveryAddress(Integer daId);
}