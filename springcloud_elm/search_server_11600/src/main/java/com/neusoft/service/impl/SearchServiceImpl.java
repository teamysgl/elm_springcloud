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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private FoodMapper foodMapper;
    @Autowired
    private BusinessMapper businessMapper;

    static private final HashSet<Character> skipwords=new HashSet<>(Arrays.asList('的','子'));

    static private List<String> getKeywords(String query){
        Result res = ToAnalysis.parse(query);
        List<String> keywords=new ArrayList<>();
        for(Term term:res){
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<term.getName().length();i++){
                Character ch=term.getName().charAt(i);
                if(!skipwords.contains(ch)) {
                    sb.append(ch);
                }
            }
            String newStr=sb.toString();
            if(!(newStr.isEmpty()||newStr.matches("^\\s+$"))){
                keywords.add(newStr);
            }
        }
        return keywords;
    }

    @Override
    public List<Food> listFoodByKeyword(String query) {
        List<String> keywords=getKeywords(query);
        return foodMapper.listFoodByKeyWord(getKeywords(query));
    }

    @Override
    public List<Business> listBusinessByKeyword(String query) {
        return businessMapper.listBusinessByKeyword(getKeywords(query));
    }
}