package com.neusoft.mapper;

import com.neusoft.po.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FoodMapper {
    @Select("select * from food where foodName like concat('%', #{keyword}, '%')  or foodExplain like concat('%', #{keyword}, '%')")
    public List<Food> listFoodByKeyWord(String keyword);
}
