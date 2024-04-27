package com.neusoft.service.impl;

import com.neusoft.mapper.PointMapper;
import com.neusoft.mapper.UserMapper;
import com.neusoft.po.Point;
import com.neusoft.po.User;
import com.neusoft.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PointServiceImpl implements PointService {
    @Autowired
    private PointMapper pointMapper;

    @Autowired
    private UserMapper userMapper0;

    @Override
    public List<Point> listOutofDatePoint(String userId, String token) throws ParseException {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(token);
        if (userMapper0.getUserByIdByPass(user) == null) return null;

        List<Point> list = new ArrayList<>();

        try {
            list = pointMapper.listPointsByUserId(userId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Point i : list) {
                i.setOutDate(sdf.format(new Date(sdf.parse(i.getPointDate()).getTime() + 15552000000L)));
                sdf.parse(i.getPointDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Point> res = new ArrayList<Point>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        for (Point i : list) {
            if (sdf.parse(i.getOutDate()).before(now)) {
                res.add(i);
            }
        }
        return res;
    }

    @Override
    public List<Point> listAvailablePoint(String userId, String token) throws ParseException {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(token);

        if(userMapper0.getUserByIdByPass(user) == null) return null;

        List<Point> list = new ArrayList<>();

        try {
            list = pointMapper.listPointsByUserId(userId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Point i : list) {
                i.setOutDate(sdf.format(new Date(sdf.parse(i.getPointDate()).getTime() + 15552000000L)));
                sdf.parse(i.getPointDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Point> res = new ArrayList<Point>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        for (Point i : list) {
            if (!sdf.parse(i.getOutDate()).before(now)) {
                res.add(i);
            }
        }
        return res;
    }
    
    //积分系统新增方法
    @Override
    public int addPointByPointId(String userId,String date,Integer pointNum)
    {
    	Point point=new Point(null,userId,date,pointNum,null);
    	pointMapper.addPointByPointId(point);
    	return 1;
    }
    
    @Override
    public int updatePointByPointId(String PointId,int pointNum)
    {
    	Point point =new Point(PointId,null,null,pointNum,null);
    	pointMapper.setPointByPointId(point);
    	return 1;
    }
    
    public int deletePointByPointId(String pointId)
    {
    	
    	pointMapper.deletePointByPointId(pointId);
    	return 1;
    }
}
