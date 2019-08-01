package com.sigma.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-10
 * desc:
 **/
@Controller
public class HomeController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
