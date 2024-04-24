//使用策略模式

package com.neusoft.controller;

import com.neusoft.po.Business;
import com.neusoft.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusinessSearchStrategy implements SearchStrategy<Business> {

    @Autowired
    private SearchService searchService;

    @Override
    public List<Business> search(String keyword) throws Exception {
        return searchService.listBusinessByKeyword(keyword);
    }
}