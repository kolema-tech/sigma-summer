package com.sigma.web;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/12/15-23:08
 * desc:
 **/
public class IpAddressUtil {


    public static String getUserAgentIp() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        String realIpHeader = request.getHeader("X-Real-IP");
        String xffHeader = request.getHeader("X-Forwarded-For");

        if (StringUtils.hasLength(xffHeader) && !"unKnown".equalsIgnoreCase(xffHeader)) {
            int index = xffHeader.indexOf(",");
            return index != -1 ? xffHeader.substring(0, index) : xffHeader;
        }

        xffHeader = realIpHeader;

        if (StringUtils.hasLength(xffHeader) && !"unKnown".equalsIgnoreCase(xffHeader)) {
            return xffHeader;
        }
        if (StringUtils.isEmpty(xffHeader) || "unknown".equalsIgnoreCase(xffHeader)) {
            xffHeader = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(xffHeader) || "unknown".equalsIgnoreCase(xffHeader)) {
            xffHeader = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(xffHeader) || "unknown".equalsIgnoreCase(xffHeader)) {
            xffHeader = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(xffHeader) || "unknown".equalsIgnoreCase(xffHeader)) {
            xffHeader = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(xffHeader) || "unknown".equalsIgnoreCase(xffHeader)) {
            xffHeader = request.getRemoteAddr();
        }
        return xffHeader;
    }
}
