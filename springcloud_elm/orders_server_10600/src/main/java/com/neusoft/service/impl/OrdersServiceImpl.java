package com.neusoft.service.impl;

import com.neusoft.mapper.*;
import com.neusoft.po.*;
import com.neusoft.service.OrdersService;
import com.neusoft.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetailetMapper orderDetailetMapper;

    @Autowired
    private DeliveryAddressMapper deliveryAddressMapper;

    @Override
    @Transactional
    public int createOrders(Orders orders) {
        Cart cart = new Cart();
        cart.setUserId(orders.getUserId());
        cart.setBusinessId(orders.getBusinessId());
        List<Cart> cartList = cartMapper.listCart(cart);

        orders.setOrderDate(CommonUtil.getCurrentDate());
        ordersMapper.saveOrders(orders);
        int orderId = orders.getOrderId();

        DeliveryAddress dA = deliveryAddressMapper.getDeliveryAddressById(orders.getDaId());
        dA.setUsed(1);
        deliveryAddressMapper.updateDeliveryAddress(dA);

        List<OrderDetailet> list = new ArrayList<>();
        for (Cart c : cartList) {
            OrderDetailet od = new OrderDetailet();
            od.setOrderId(orderId);
            od.setFoodId(c.getFoodId());
            od.setQuantity(c.getQuantity());
            list.add(od);
        }
        orderDetailetMapper.saveOrderDetailetBatch(list);

        cartMapper.removeCart(cart);

        return orderId;
    }

    @Override
    public Orders getOrdersById(Integer orderId) {
        return ordersMapper.getOrdersById(orderId);
    }

    @Override
    public List<Orders> listOrdersByUserId(String userId) {
        return ordersMapper.listOrdersByUserId(userId);
    }

    @Override
    public int payOrdersById(Integer orderId, String userId, Double orderTotal, Double reduction) {
        return ordersMapper.payOrdersById(orderId);
    }
}
