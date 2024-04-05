package com.neusoft.mapper;

import com.neusoft.po.Business;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BusinessMapper {
    public List<Business> listBusinessByKeyword(List<String> keywords);
}