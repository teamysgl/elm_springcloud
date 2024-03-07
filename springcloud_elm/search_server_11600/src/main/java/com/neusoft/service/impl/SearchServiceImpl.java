package com.neusoft.service.impl;

import com.neusoft.mapper.BusinessMapper;
import com.neusoft.mapper.FoodMapper;
import com.neusoft.po.Business;
import com.neusoft.po.Food;
import com.neusoft.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private FoodMapper foodMapper;
    @Autowired
    private BusinessMapper businessMapper;

    @Override
    public List<Food> listFoodByKeyword(String keyword) {
        return foodMapper.listFoodByKeyWord(keyword);
    }

    @Override
    public List<Business> listBusinessByKeyword(String keyword) {
        return businessMapper.listBusinessByKeyword(keyword);
    }
}