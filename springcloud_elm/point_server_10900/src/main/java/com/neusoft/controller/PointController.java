package com.neusoft.controller;

import com.neusoft.po.CommonResult;
import com.neusoft.po.Point;
import com.neusoft.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PointController")
public class PointController {
    @Autowired
    private PointService pointService;

    @GetMapping("/OutofDatePoints")
    public CommonResult<List<Point>> listOutofDatePoint(@RequestParam("userId") String userId, @RequestHeader("Authorization") String token) throws Exception {
        return new CommonResult<>(200,"success",pointService.listOutofDatePoint(userId,token));
    }

    @GetMapping("/AvailablePoints")
    public CommonResult<List<Point>> listAvailablePoint(@RequestParam("userId") String userId, @RequestHeader("Authorization") String token) throws Exception {
        return new CommonResult<>(200,"success",pointService.listAvailablePoint(userId,token));
//        return null;
    }
    
    @PostMapping("/addPointByPointId/{userId}/{date}/{pointNum}")
    public CommonResult<Integer> addPointByPointId(@PathVariable("userId") String userId,@PathVariable("date") String date,@PathVariable("pointNum") Integer pointNum)
    {
    	System.out.println("addPointById/"+userId+"/"+date+"/"+pointNum);
    	return new CommonResult(200,"success",pointService.addPointByPointId(userId, date, pointNum));
    }
    
    @PostMapping("/updatePointNumById/{pointId}/{PointNum}")
    public CommonResult<Integer> updatePointNumById(@PathVariable String pointId,@PathVariable int PointNum)
    {
    	System.out.println("pointId="+pointId+"pointNum="+PointNum);
    	return new CommonResult(200,"success",pointService.updatePointByPointId(pointId, PointNum));
    }
    
    @DeleteMapping("/deletePointByPointId/{pointId}")
    public CommonResult<Integer> deletePointByPointId(@PathVariable("pointId") String pointId)
    {
    	return new CommonResult(200,"success",pointService.deletePointByPointId(pointId));
    }
    
}
