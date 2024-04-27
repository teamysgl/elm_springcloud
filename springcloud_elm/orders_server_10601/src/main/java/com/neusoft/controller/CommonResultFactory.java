package com.neusoft.controller;

import com.neusoft.po.CommonResult;
import org.springframework.stereotype.Component;

@Component
public class CommonResultFactory {

    public <T> CommonResult<T> createSuccessResult(T data) {
        return new CommonResult<>(200, "success", data);
    }
}
