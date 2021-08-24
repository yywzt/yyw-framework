package com.yyw.example.log.controller;

import com.yyw.api.exception.BusinessException;
import com.yyw.api.model.ResponseData;
import com.yyw.example.log.dto.LogDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2020/12/10 16:57
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @RequestMapping("/index")
    public Object index() {
        return 123;
    }

    @RequestMapping("/json")
    public Object json(@RequestBody LogDTO logDTO) {
        return logDTO;
    }

    @RequestMapping("/exception")
    public ResponseData<Object> exception() throws BusinessException {
        throw new BusinessException("123123123","error");
    }
}
