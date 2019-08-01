package com.sigma.xxljob;

import com.sigma.sigmacore.PrintLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.experimental.var;

import java.text.MessageFormat;

/**
 * @author zen peng.
 * @version 1.0.3
 * date-time: 2018/12/8-16:40
 * desc:
 **/
public abstract class BaseJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String parameters) {

        var clsName = this.getClass().getName();

        PrintLog printLog = (message, detail) -> {
            XxlJobLogger.log(message, detail);
        };

        try {
            printLog.print(MessageFormat.format("XXL-JOB:{0} Begin.", clsName), "");

            executeCore(parameters, printLog);

            printLog.print(MessageFormat.format("XXL-JOB:{0} End.", clsName), "");

            return SUCCESS;

        } catch (Exception e) {

            printLog.print(MessageFormat.format("XXL-JOB:{0} ERROR.", clsName), e.getStackTrace().toString());

            return FAIL;
        }
    }

    /**
     * 执行的核心方法
     *
     * @param parameter 参数
     * @param printLog  打印日志
     */
    protected abstract void executeCore(String parameter, PrintLog printLog);
}
