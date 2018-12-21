package com.sigma.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sigma.web.ThreadContextHolder;
import com.sigma.web.filter.RequestWrapper;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 基本拦截器
 *
 * @author ware zhang
 * @version 1.0.0
 * @date 2018/10/30 16:55
 */
@Slf4j
public class RqContextInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestWrapper requestWrapper;
        if (request instanceof RequestWrapper) {
            requestWrapper = (RequestWrapper) request;
            String bodyString = RequestWrapper.getBodyString(requestWrapper);
            ObjectMapper mapper = new ObjectMapper();

            Map m = mapper.readValue(bodyString, Map.class);
            if (m != null) {
                ThreadContextHolder.getPathDict().entrySet().forEach(stringClassEntry -> {

                    //header
                    //data
                    //data.orderId
                    //data.A.B
                    //data orderId
                    var casArray = stringClassEntry.getKey().split("\\.");

                    Map currentMap = m;
                    Map preMap = m;
                    String currentName = stringClassEntry.getKey();
                    Object currentValue = null;
                    for (int i = 0; i < casArray.length; i++) {
                        currentName = casArray[i];
                        currentMap = (Map) m.get(currentName);
                        if (currentMap == null) {
                            break;
                        } else {
                            currentValue = currentMap.get(currentName);
                        }
                    }

                    if (currentMap != null) {
                        ThreadContextHolder.setValueMap(currentName, currentMap, stringClassEntry.getValue());
                    } else {
                        ThreadContextHolder.setValue(currentName, currentValue);
                    }
                });
            }
        }

        return true;
    }
}
