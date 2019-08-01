package com.sigma.scheduler.schedule;

import com.sigma.scheduler.CronJobConfig;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/11-18:42
 * desc:
 **/
@Component
@Slf4j
public class SigmaTriggerSchedulerFactoryBean extends SchedulerFactoryBean {

    /** 日志 */

    /**
     * Spring 上下文
     */
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void registerJobsAndTriggers() throws SchedulerException {
        try {
            // 获取所有bean name
            String[] beanNames = applicationContext
                    .getBeanNamesForType(BaseSigmaJob.class);

            for (String beanName : beanNames) {
                Class<?> targetClass = applicationContext.getType(beanName);

                Object targetObject = applicationContext.getBean(beanName);
                // 获取时间表达式
                String cronExpression = "";
                String targetMethod = "";

                CronJobConfig triggerMethod = null;
                // 确定标记了MyTriggerMethod注解的方法名
                Method[] methods = targetClass.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(CronJobConfig.class)) {
                        targetMethod = method.getName();
                        triggerMethod = method.getAnnotation(CronJobConfig.class);
                        cronExpression = triggerMethod.expression();

                        // 注册定时器业务类
                        registerJobs(targetObject, targetMethod, beanName,
                                cronExpression);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 註冊
     *
     * @param targetObject   bean
     * @param targetMethod   方法
     * @param beanName       bean名稱
     * @param cronExpression cron表達式
     * @throws Exception 異常
     */
    private void registerJobs(Object targetObject, String targetMethod,
                              String beanName, String cronExpression) throws Exception {

        var jobName = beanName + targetMethod;
        // 声明包装业务类
        var jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setTargetObject(targetObject);
        jobDetailFactoryBean.setTargetMethod(targetMethod);
        jobDetailFactoryBean.setBeanName(jobName);
        jobDetailFactoryBean.setName(jobName);
        jobDetailFactoryBean.setGroup(jobName);
        jobDetailFactoryBean.setConcurrent(false);
        jobDetailFactoryBean.afterPropertiesSet();

        // 获取JobDetail
        JobDetail jobDetail = jobDetailFactoryBean.getObject();

        // 声明定时器
        var cronTriggerBean = new CronTriggerFactoryBean();
        cronTriggerBean.setJobDetail(jobDetail);
        cronTriggerBean.setCronExpression(cronExpression);
        cronTriggerBean.setName(jobName);
        cronTriggerBean.setGroup(jobName);
        cronTriggerBean.setBeanName(jobName);
        cronTriggerBean.afterPropertiesSet();

        setTriggers(new Trigger[]{cronTriggerBean.getObject()});

        super.registerJobsAndTriggers();
    }
}
