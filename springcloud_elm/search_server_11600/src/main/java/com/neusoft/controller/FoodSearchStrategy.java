package com.neusoft.controller;

import com.neusoft.po.Food;
import com.neusoft.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FoodSearchStrategy implements SearchStrategy<Food> {

    @Autowired
    private SearchService searchService;

    @Override
    public List<Food> search(String keyword) throws Exception {
        return searchService.listFoodByKeyword(keyword);
    }
}