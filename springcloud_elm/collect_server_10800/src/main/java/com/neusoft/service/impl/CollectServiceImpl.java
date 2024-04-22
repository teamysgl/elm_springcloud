package com.neusoft.service.impl;

import com.neusoft.mapper.CollectMapper;
import com.neusoft.po.Collect;
import com.neusoft.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qq
 * @version 1.0
 */
@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;

    @Override
    public List<Collect> getCollectList(Collect collect) {
        return collectMapper.listCollect(collect);
    }

    @Override
    public Integer addCollect(Collect collect) {
        return collectMapper.insertCollect(collect);
    }

    @Override
    public Integer deleteCollect(Collect collect) {
        return collectMapper.deleteCollectById(collect);
    }

    @Override
    public Integer updateCollect(Collect collect) {
        return collectMapper.updateCollectById(collect);
    }
}
