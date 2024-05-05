package com.neusoft.service;

import com.neusoft.po.Business;
import com.neusoft.po.Food;

import java.util.List;

public interface SearchService {
    List<Food> listFoodByKeyword(String query);

    List<Business> listBusinessByKeyword(String query);
}
