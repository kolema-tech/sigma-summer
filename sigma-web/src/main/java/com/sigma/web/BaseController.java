package com.sigma.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huston.peng
 * @version 1.0.0
 * date-time: 2019/6/16-23:40
 * desc:
 **/
@Component
public class BaseController {

    @Autowired
    HttpServletRequest request;

}
