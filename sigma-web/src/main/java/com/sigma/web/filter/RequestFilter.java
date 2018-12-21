package com.sigma.web.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求过滤器
 * 因为request.getInputStream 没有重写reset方法，所以读取完一次字节流后，指针没有恢复，其他地方再调不会获取字节流
 * 所以需要重新封装参数解析器
 *
 * @author ware zhang
 * @version 1.0.0
 * @date 2018/10/30 17:18
 */
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof RequestWrapper)) {
            //将字节流重新封装进
            RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
            filterChain.doFilter(requestWrapper, servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
