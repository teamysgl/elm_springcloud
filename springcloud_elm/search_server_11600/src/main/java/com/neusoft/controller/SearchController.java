//工厂模式
/*创建了一个 CommonResultFactory 工厂类，它负责创建 CommonResult 对象。在 OrdersController 类中，我们将 CommonResultFactory 注入，并使用工厂类来创建 CommonResult 对象，以避免在每个方法中重复创建相同类型的 CommonResult 对象。*/

package com.neusoft.controller;

import com.neusoft.po.Business;
import com.neusoft.po.CommonResult;
import com.neusoft.po.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/SearchController")
public class SearchController {

    private final SearchStrategy<Food> foodSearchStrategy;
    private final SearchStrategy<Business> businessSearchStrategy;

    @Autowired
    public SearchController(SearchStrategy<Food> foodSearchStrategy, SearchStrategy<Business> businessSearchStrategy) {
        this.foodSearchStrategy = foodSearchStrategy;
        this.businessSearchStrategy = businessSearchStrategy;
    }

    @GetMapping("/listFoodByKeyword/{keyword}")
    public CommonResult<List<Food>> listFoodByKeyword(@PathVariable("keyword") String keyword) throws Exception {
        List<Food> list = foodSearchStrategy.search(keyword);
        return new CommonResult<>(200, "success", list);
    }

    @GetMapping("/listBusinessByKeyword/{keyword}")
    public CommonResult<List<Business>> listBusinessByKeyword(@PathVariable("keyword") String keyword) throws Exception {
        List<Business> list = businessSearchStrategy.search(keyword);
        return new CommonResult<>(200, "success", list);
    }
}
