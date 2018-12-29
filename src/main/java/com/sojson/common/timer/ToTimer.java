package com.sojson.common.timer;

import com.sojson.permission.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * <p>定时任务恢复数据</p>
 *
 * @author kqyang
 * @version 1.0
 * @date 2018/12/29 15:50
 */
@Component
public class ToTimer {

    private Logger logger = Logger.getLogger(ToTimer.class);

    @Resource
    RoleService roleService;

    /**
     * <p>程序定时任务</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2018/12/29 17:07
     */
    @Scheduled(cron = "0/20 * * * * ? ")
    public void run() {
        /**
         * 调用存储过程，重新创建表，插入初始化数据。
         */
        roleService.initData();
        System.out.println(System.currentTimeMillis());
    }

    /**
     * <p>以一个固定的延迟时间5s调用一次执行</p>
     * <p>这个周期是以上一个周期调用任务的##完成时间##为基准，在上一个任务完成之后，5s后再执行</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2018/12/29 17:07
     */
    @Scheduled(fixedDelay = 5000)
    public void demo1() {
        logger.info("定时任务demo1开始...");
        long begin = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        logger.info("定时任务demo1结束，共耗时：[" + (end - begin) + "]毫秒");
    }

    /**
     * <p>以一个固定的延迟时间5s调用一个执行</p>
     * <p>这个周期是以上一个周期调用任务的##开始时间##为准，在上一个任务开始执行后5s后再执行</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2018/12/29 17:24
     */
    @Scheduled(fixedRate = 5000)
    public void demo2() {
        logger.info("定时任务demo2开始...");
        long begin = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        logger.info("定时任务demo2结束，共耗时：[" + (end - begin) + "]毫秒");
    }

    /**
     * <p>在每天的13时30分执行一次</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2018/12/29 17:29
     */
    @Scheduled(cron = "0 34 13 * * ?")
    public void demo3() {
        logger.info("定时任务demo3开始...");
        long begin = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        logger.info("定时任务demo3结束，共耗时：[" + (end - begin) + "]毫秒");
    }
}
