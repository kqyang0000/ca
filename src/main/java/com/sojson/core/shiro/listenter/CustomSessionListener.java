package com.sojson.core.shiro.listenter;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import com.sojson.core.shiro.session.ShiroSessionRepository;
/**
 * <p>shiro 会话 监听</p>
 *
 * @author kqyang
 * @version 1.0
 * @date 2019/1/2 15:21
 */
public class CustomSessionListener implements SessionListener {

    private ShiroSessionRepository shiroSessionRepository;

    /**
     * 一个回话的生命周期开始
     */
    @Override
    public void onStart(Session session) {
        //TODO
        System.out.println("on start");
    }
    /**
     * 一个回话的生命周期结束
     */
    @Override
    public void onStop(Session session) {
        //TODO
        System.out.println("on stop");
    }

    @Override
    public void onExpiration(Session session) {
        shiroSessionRepository.deleteSession(session.getId());
    }

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

}

