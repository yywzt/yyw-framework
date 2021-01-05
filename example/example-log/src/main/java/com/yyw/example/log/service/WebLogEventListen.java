package com.yyw.example.log.service;

import com.yyw.framework.log.event.WebLogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2021/1/5 10:45
 */
@Component
public class WebLogEventListen {

    private Logger logger = LoggerFactory.getLogger(WebLogEventListen.class);

    @EventListener
    public void webLogListen(WebLogEvent webLogEvent) {
        logger.info("webLogEvent Listener... result: {}", webLogEvent.getRequestLogDTO());
    }
}
