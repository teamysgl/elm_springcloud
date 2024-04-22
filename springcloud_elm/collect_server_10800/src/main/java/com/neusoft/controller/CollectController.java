package com.neusoft.controller;

import com.neusoft.po.Collect;
import com.neusoft.po.CommonResult;
import com.neusoft.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qq
 * @version 1.0
 */
@RefreshScope
@RestController
@RequestMapping("/CollectController")
public class CollectController {
    @Autowired
    private CollectService collectService;

    @GetMapping("/listCollect/{userId}")
    public CommonResult<List<Collect>> listCollect(@PathVariable("userId") String userId) throws Exception {
        Collect param = new Collect();
        param.setUserId(userId);
        List<Collect> list = collectService.getCollectList(param);
        return new CommonResult<>(200, "success", list);
    }
    @PostMapping("/addCollect/{userId}/{businessId}")
    public CommonResult<Integer> addCollect(
            @PathVariable("userId") String userId,
            @PathVariable("businessId") Integer businessId) throws Exception {
        Collect param = new Collect();
        param.setUserId(userId);
        param.setBusinessId(businessId);
        Integer result = collectService.addCollect(param);
        return new CommonResult<>(200,"success", result);
    }
    @DeleteMapping("/deleteCollect/{userId}/{businessId}")
    public CommonResult<Integer> deleteCollect(@PathVariable("userId") String userId,
                                               @PathVariable("businessId") Integer businessId) throws Exception {
        Collect param = new Collect();
        param.setUserId(userId);
        param.setBusinessId(businessId);
        Integer result = collectService.deleteCollect(param);
        return new CommonResult<>(200, "success", result);
    }
    @PostMapping("/updateCollect/{userId}/{foodId}")
    public CommonResult<Integer> updateCollectFood(@PathVariable("userId") String userId,
                                                   @PathVariable("businessId") Integer businessId) throws Exception {
        Collect param = new Collect();
        param.setUserId(userId);
        param.setBusinessId(businessId);
        Integer result = collectService.updateCollect(param);
        return new CommonResult<>(200, "success", result);
    }

}
