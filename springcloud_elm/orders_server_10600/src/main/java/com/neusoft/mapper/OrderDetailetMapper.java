package com.neusoft.mapper;

import com.neusoft.po.OrderDetailet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailetMapper {
	 public int saveOrderDetailetBatch(List<OrderDetailet> list);
	 
	 public List<OrderDetailet> listOrderDetailetByOrderId(Integer orderOd);
}
