package com.yyw.example.log.controller;

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
}
