package com.yyw.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2021/8/11 11:30
 */
public class ServletUtil{
    private static final Logger logger = LoggerFactory.getLogger(ServletUtil.class);
    private static final String MULTI_PROXY_ADDRESS_SEPARATOR = ",";

    /**
     * 获取IP地址
     *
     * @param request 请求体
     * @return IP地址
     */
    public static String getIpAddr(HttpServletRequest request){
        String ipAddress = "";
        if(null == request){
            return ipAddress;
        }
        try{
            ipAddress = request.getHeader("x-forwarded-for");
            logger.debug("IpAddress from request header [x-forwared-for] :" + StringUtils.trimToEmpty(ipAddress));
            if(!isValidIpAddress(ipAddress)){
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            logger.debug("IpAddress from request header [Proxy-Client-IP] :" + StringUtils.trimToEmpty(ipAddress));
            if(!isValidIpAddress(ipAddress)){
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            logger.debug("IpAddress from request header [WL-Proxy-Client-IP] :" + StringUtils.trimToEmpty(ipAddress));
            if(!isValidIpAddress(ipAddress)){
                ipAddress = request.getRemoteAddr();
                logger.debug("IpAddress from request [RemoteAddr] :" + StringUtils.trimToEmpty(ipAddress));
                if(ipAddress.equals("127.0.0.1")){
                    ipAddress = getLocalHost();
                }
            }
            if(ipAddress != null && ipAddress.indexOf(MULTI_PROXY_ADDRESS_SEPARATOR) > 0){
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }catch(Exception e){
            logger.error("Failed to get client ip address.", e);
        }
        return StringUtils.trimToEmpty(ipAddress);
    }

    public static String getLocalHost(){
        InetAddress inetAddress = null;
        try{
            inetAddress = InetAddress.getLocalHost();
        }catch(UnknownHostException e){
            logger.error("Failed to read ip address from local.", e);
        }
        return inetAddress.getHostAddress();
    }

    /**
     * 检查IP地址合法性
     *
     * @param ipAddress IP地址
     * @return true：合法；false：非法
     */
    private static boolean isValidIpAddress(String ipAddress){
        return null != ipAddress && 0 < ipAddress.length() && !"unknown".equalsIgnoreCase(ipAddress);
    }
}
