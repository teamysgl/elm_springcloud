package com.neusoft.mapper;

import com.neusoft.po.Business;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BusinessMapper {
    @Select("select * from business where businessName like concat('%', #{keyword}, '%')  or businessAddress like concat('%', #{keyword}, '%')  or businessExplain like concat('%', #{keyword}, '%')")
    public List<Business> listBusinessByKeyword(String keyword);
}