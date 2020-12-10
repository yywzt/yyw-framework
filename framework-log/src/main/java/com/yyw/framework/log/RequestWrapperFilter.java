package com.yyw.framework.log;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author yzt
 */
public class RequestWrapperFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String header = request.getHeader("Content-Type");
            if ("application/json".equals(header) || "application/json;charset=UTF-8".equals(header)) {
                requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
            }
        }
        //获取request请求中的流，将取出来的字符串保存在缓存中，同时再将该字符串再次转换成流，然后把它放入到新request对象中。
        if (requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        //
    }

    @Override
    public void destroy() {
        //
    }
}
