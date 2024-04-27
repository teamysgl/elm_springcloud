package com.neusoft.service;

import com.neusoft.po.Business;
import com.neusoft.po.Food;

import java.util.List;

public interface SearchService {
    public List<Food> listFoodByKeyword(String query);

    public List<Business> listBusinessByKeyword(String query);
}
