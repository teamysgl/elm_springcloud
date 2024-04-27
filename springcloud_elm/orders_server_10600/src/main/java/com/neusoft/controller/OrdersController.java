package com.neusoft.controller;

import com.neusoft.po.CommonResult;
import com.neusoft.po.Orders;
import com.neusoft.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import com.neusoft.controller.CommonResultFactory;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/OrdersController")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    private final CommonResultFactory commonResultFactory;

    public OrdersController(CommonResultFactory commonResultFactory) {
        this.commonResultFactory = commonResultFactory;
    }

    @PostMapping("/createOrders/{userId}/{businessId}/{daId}/{orderTotal}")
    public CommonResult<Integer> createOrders(
            @PathVariable("userId") String userId,
            @PathVariable("businessId") Integer businessId,
            @PathVariable("daId") Integer daId,
            @PathVariable("orderTotal") Double orderTotal
    ) throws Exception {
        Orders param=new Orders();
        param.setUserId(userId);
        param.setBusinessId(businessId);
        param.setDaId(daId);
        param.setOrderTotal(orderTotal);
        int orderId=ordersService.createOrders(param);
        return commonResultFactory.createSuccessResult(orderId);
    }

    @GetMapping("/getOrdersById/{orderId}")
    public CommonResult<Orders> getOrdersById(@PathVariable("orderId") Integer orderId) throws Exception {
        Orders orders=ordersService.getOrdersById(orderId);
        return commonResultFactory.createSuccessResult(orders);
    }

    @GetMapping("/listOrdersByUserId/{userId}")
    public CommonResult<List<Orders>> listOrdersByUserId(@PathVariable("userId") String userId) throws Exception {
        List<Orders> list=ordersService.listOrdersByUserId(userId);
        return commonResultFactory.createSuccessResult(list);
    }

    @PutMapping("/Orders/{orderId}")
    public CommonResult<Integer>payOrdersById(@PathVariable("orderId") Integer orderId)throws Exception
    {
    	int result=ordersService.payOrdersById(orderId, null, null, null);
    	return commonResultFactory.createSuccessResult(result);
    }
    
    
//    @PutMapping("/Orders")
//    public int payOrdersById(OrderState orderState, @RequestHeader("Authorization") String token) throws Exception {
//        return ordersService.payOrdersById(orderState.getOrderId(), orderState.getUserId(), orderState.getOrderTotal(), orderState.getReduction(),token);
//    }
}