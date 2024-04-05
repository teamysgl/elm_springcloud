package com.neusoft.mapper;


import com.neusoft.po.Point;

import feign.Param;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PointMapper {
    @Select("select * from point where userId=#{userId}")
    public List<Point> listPointsByUserId(String userId);

    @Update("update point set pointNum=#{pointNum} where pointId=#{pointId}")
    public void setPointByPointId(Point point);

    @Delete("delete from point where pointId=#{pointId}")
    public void deletePointByPointId(String pointId);

    @Select("insert into point(userId,pointDate,pointNum,outDate) values(#{userId},#{pointDate},#{pointNum},#{outDate})")
    @Options(useGeneratedKeys=true,keyProperty="pointId",keyColumn="pointId")
    public void addPointByPointId(Point point);
    
}
