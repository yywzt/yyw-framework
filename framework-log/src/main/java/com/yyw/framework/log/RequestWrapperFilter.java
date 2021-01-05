package com.yyw.framework.log;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * @author yzt
 */
public class RequestWrapperFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (!isRequestValid(httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        if (!(httpServletRequest instanceof ContentCachingRequestWrapper)) {
            httpServletRequest = new ContentCachingRequestWrapper(httpServletRequest);
        }
        if (!(httpServletResponse instanceof ContentCachingResponseWrapper)) {
            httpServletResponse = new ContentCachingResponseWrapper(httpServletResponse);
        }
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            copyBodyToResponse(httpServletResponse);
        }
    }

    private boolean isRequestValid(HttpServletRequest request) {
        try {
            new URI(request.getRequestURL().toString());
            return true;
        } catch (URISyntaxException ex) {
            return false;
        }
    }

    /**
     * 防止 读取response body的数据后，导致无法返回数据
     *
     * @param response
     * @throws IOException
     */
    private void copyBodyToResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        Objects.requireNonNull(responseWrapper).copyBodyToResponse();
    }

}
