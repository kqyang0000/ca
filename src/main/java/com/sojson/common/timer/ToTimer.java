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
     * <p>
     * 一个cron表达式有至少6个(也可能是7个)有空格分隔的时间元素。
     * 按顺序依次为：
     * 1 秒(0~59)
     * 2 分钟(0~59)
     * 3 小时(0~23)
     * 4 天(0~31)
     * 5 月(0~11)
     * 6 星期(1~7 依次为SUN,MON,TUE,WED,THU,FRI,SAT)
     * 7 年(1970~2099)
     * 0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
     * 0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时
     * 0 0 12 ? * WED 表示每个星期三中午12点
     * 0 0 12 * * ? 每天中午12点触发
     * 0 15 10 ? * * 每天上午10:15触发
     * 0 15 10 * * ? 每天上午10:15触发·
     * 0 15 10 * * ? * 每天上午10:15触发
     * 0 15 10 * * ? 2005 2005年的每天上午10:15触发
     * 0 * 14 * * ? 在每天下午2点到下午2:59期间的每1分钟触发
     * 0 0/5 14 * * ? 在每天下午2点到下午2:55期间的每5分钟触发
     * 0 0/5 14,18 * * ? 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
     * 0 0-5 14 * * ? 在每天下午2点到下午2:05期间的每1分钟触发
     * 0 10,44 14 ? 3 WED 每年三月的星期三的下午2:10和2:44触发
     * 0 15 10 ? * MON-FRI 周一至周五的上午10:15触发
     * 0 15 10 15 * ? 每月15日上午10:15触发
     * 0 15 10 L * ? 每月最后一日的上午10:15触发
     * 0 15 10 ? * 6L 每月的最后一个星期五上午10:15触发
     * 0 15 10 ? * 6L 2002-2005 2002年至2005年的每月的最后一个星期五上午10:15触发
     * 0 15 10 ? * 6#3 每月的第三个星期五上午10:15触发
     * </p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2018/12/29 17:29
     */
    @Scheduled(cron = "0 30 13 * * ?")
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
