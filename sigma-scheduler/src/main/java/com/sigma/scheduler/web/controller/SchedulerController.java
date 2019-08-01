package com.sigma.scheduler.web.controller;

import com.sigma.scheduler.service.ServiceManager;
import com.sigma.sigmacore.web.SigmaResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/26-20:52
 * desc: 調度器
 **/
@RestController
@RequestMapping("/api/scheduler")
@Api
public class SchedulerController {

    @Autowired
    ServiceManager serviceManager;

    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有的JOB")
    public SigmaResponse getAllJobs() {
        return SigmaResponse.create(serviceManager.getAllJobs());
    }

    @RequestMapping(value = "/resume/{serviceName}", method = RequestMethod.PUT)
    @ApiOperation(value = "resume")
    public SigmaResponse resume(String serviceName) {
        serviceManager.resume(serviceName);
        return SigmaResponse.create(null);
    }

    @RequestMapping(value = "/pause/{serviceName}", method = RequestMethod.PUT)
    @ApiOperation(value = "pause")
    public SigmaResponse pause(String serviceName) {
        serviceManager.pause(serviceName);
        return SigmaResponse.create(null);
    }

    @RequestMapping(value = "/cron/{serviceName}/{cron}", method = RequestMethod.PUT)
    @ApiOperation(value = "cron")
    public SigmaResponse cron(String serviceName, String cron) {
        serviceManager.cronUpdate(serviceName, cron);
        return SigmaResponse.create(null);
    }

    @RequestMapping(value = "/run/{serviceName}", method = RequestMethod.PUT)
    @ApiOperation(value = "run")
    public SigmaResponse run(String serviceName) {
        serviceManager.run(serviceName);
        return SigmaResponse.create(null);
    }
}
