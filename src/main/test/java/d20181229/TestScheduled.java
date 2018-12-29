package d20181229;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>定时任务测试类</p>
 *
 * @author kqyang
 * @version 1.0
 * @date 2018/12/29 17:35
 */
public class TestScheduled {

    /**
     * <p>
     * 这个before方法在所有的测试方法之前执行，并且只执行一次，所有做junit单元测试时一
     * 些初始化的工作可以在这个方法里面执行，比如在before方法里面初始化ApplicationContext和userService
     * </p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2018/12/29 17:36
     */
    @Before
    public void before() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-timer.xml");
    }

    /**
     * <p>测试定时任务的执行</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2018/12/29 17:40
     */
    @Test
    public void testTask() throws InterruptedException {
        System.out.println("开始执行了...");
        Thread.sleep(1000000);
        System.out.println("结束执行了...");
    }
}
