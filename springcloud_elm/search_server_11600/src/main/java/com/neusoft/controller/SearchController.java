package com.neusoft.controller;

import com.neusoft.po.Business;
import com.neusoft.po.CommonResult;
import com.neusoft.po.Food;
import com.neusoft.service.SearchService;
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
    @Autowired
    private SearchService searchService;

    @GetMapping("/listFoodByKeyword/{keyword}")
    public CommonResult<List<Food>> listFoodByKeyword(@PathVariable("keyword") String keyword) throws Exception {
        List<Food> list= searchService.listFoodByKeyword(keyword);
        return new CommonResult<>(200,"success",list);
    }

    @GetMapping("/listBusinessByKeyword/{keyword}")
    public CommonResult<List<Business>> listBusinessByKeyword(@PathVariable("keyword") String keyword) throws Exception {
        List<Business> list= searchService.listBusinessByKeyword(keyword);
        return new CommonResult<>(200,"success",list);
    }
}
