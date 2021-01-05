package com.yyw.framework.log.event;

import com.yyw.framework.log.dto.RequestLogDTO;
import org.springframework.context.ApplicationEvent;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2021/1/5 10:23
 */
public class WebLogEvent extends ApplicationEvent {

    private RequestLogDTO requestLogDTO;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public WebLogEvent(Object source, RequestLogDTO requestLogDTO) {
        super(source);
        this.requestLogDTO = requestLogDTO;
    }

    public RequestLogDTO getRequestLogDTO() {
        return requestLogDTO;
    }
}
