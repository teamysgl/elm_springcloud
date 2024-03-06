package com.neusoft.feign;

import com.neusoft.po.CommonResult;
import com.neusoft.po.Food;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FooFeignClientCallBack implements FoodFeignClient{
    @Override
    public CommonResult<List<Food>> listFoodByBusinessId(Integer businessId) {
        return new CommonResult<>(403,"feign触发了熔断降级",null);
    }
}
