package com.neusoft.controller;

import com.neusoft.po.CommonResult;
import com.neusoft.po.Food;
import com.neusoft.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/FoodController")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Value("${eureka.instance.lease-renewal-interval-in-seconds}")
    private int renewal;
    @Value("${eureka.instance.lease-expiration-duration-in-seconds}")
    private int expiration;

    @GetMapping("/listFoodByBusinessId/{businessId}")
    public CommonResult<List<Food>> listFoodByBusinessId(@PathVariable("businessId") Integer businessId) throws Exception {
        System.out.println(renewal);
        System.out.println(expiration);
        List<Food> list=foodService.listFoodByBusinessId(businessId);
        return new CommonResult<>(200,"success（10200）",list);
    }
}
