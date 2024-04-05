package com.neusoft.service.impl;

import com.neusoft.mapper.BusinessMapper;
import com.neusoft.mapper.FoodMapper;
import com.neusoft.po.Business;
import com.neusoft.po.Food;
import com.neusoft.service.SearchService;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private FoodMapper foodMapper;
    @Autowired
    private BusinessMapper businessMapper;

    @Override
    public List<Food> listFoodByKeyword(String query) {
        Result res = ToAnalysis.parse(query);
        List<String> keywords=new ArrayList<>();
        for(Term term:res){
            keywords.add(term.getName());
        }
        return foodMapper.listFoodByKeyWord(keywords);
    }

    @Override
    public List<Business> listBusinessByKeyword(String query) {
        Result res = ToAnalysis.parse(query);
        List<String> keywords=new ArrayList<>();
        for(Term term:res){
            keywords.add(term.getName());
        }
        return businessMapper.listBusinessByKeyword(keywords);
    }
}