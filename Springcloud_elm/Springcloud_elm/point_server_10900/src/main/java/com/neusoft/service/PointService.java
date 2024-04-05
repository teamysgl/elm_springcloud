package com.neusoft.service;

import java.text.ParseException;
import java.util.List;

import com.neusoft.po.Point;

public interface PointService {
    public List<Point> listOutofDatePoint(String userId, String token) throws ParseException;

    public List<Point> listAvailablePoint(String userId, String token) throws ParseException;
    
    
    public int addPointByPointId(String userId,String date,Integer pointNum);
    
    public int updatePointByPointId(String pointId,int PointNum);
    
    public int deletePointByPointId(String pointId);
    
    
}
