package com.neusoft.mapper;

import com.neusoft.po.Business;
import com.neusoft.po.Collect;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author qq
 * @version 1.0
 */
@Mapper
public interface CollectMapper {
    //public List<Collect> listCollect(Collect collect);

    //@Select("select * from collect where userId=#{userId}")
    public List<Collect> listCollect(Collect collect);
    //@Delete("delete from collect where collectId=#{collectId}")
    public Integer deleteCollectById(Collect collect);
    @Select("insert into collect(userId,businessId) values(#{userId},#{businessId})")
    public Integer insertCollect(Collect collect);
    @Update("update collect set businessId=#{businessId} where collectId={collectId}")
    public Integer updateCollectById(Collect collect);
}
