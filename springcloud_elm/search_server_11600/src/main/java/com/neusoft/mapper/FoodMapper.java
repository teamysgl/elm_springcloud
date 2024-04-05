package com.neusoft.mapper;

import com.neusoft.po.Food;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoodMapper {
    public List<Food> listFoodByKeyWord(List<String> keywords);
}
