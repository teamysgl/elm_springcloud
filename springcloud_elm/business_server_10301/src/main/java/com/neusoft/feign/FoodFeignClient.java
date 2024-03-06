package com.neusoft.feign;

import com.neusoft.po.CommonResult;
import com.neusoft.po.Food;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(name = "food-server",fallback = FooFeignClientCallBack.class)
public interface FoodFeignClient {
    @GetMapping("/FoodController/listFoodByBusinessId/{businessId}")
    public CommonResult<List<Food>> listFoodByBusinessId(@PathVariable("businessId") Integer businessId);
}
