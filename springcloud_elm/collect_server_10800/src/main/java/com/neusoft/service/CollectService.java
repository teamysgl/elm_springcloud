package com.neusoft.service;

import com.neusoft.po.Business;
import com.neusoft.po.Collect;

import java.util.List;

/**
 * @author qq
 * @version 1.0
 */
public interface CollectService {
    public List<Collect> getCollectList(Collect collect);

    public Integer addCollect(Collect collect);

    public Integer deleteCollect(Collect collect);

    public Integer updateCollect(Collect collect);


}
