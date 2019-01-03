package com.sojson.core.shiro.service.impl;

import com.sojson.common.utils.LoggerUtils;
import com.sojson.core.config.INI4j;
import com.sojson.core.shiro.service.ShiroManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * <p>动态加载权限 Service</p>
 *
 * @author kqyang
 * @version 1.0
 * @date 2019/1/2 17:51
 */
public class ShiroManagerImpl implements ShiroManager {

    /**
     * 注意/r/n前不能有空格
     */
    private static final String CRLF = "\r\n";

    @Resource
    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Override
    public String loadFilterChainDefinitions() {
        StringBuffer sb = new StringBuffer();
        // 固定权限，采用读取配置文件
        sb.append(getFixedAuthRule());
        return sb.toString();
    }

    /**
     * <p>从配额文件获取固定权限验证规则串</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2019/1/3 14:38
     */
    private String getFixedAuthRule() {
        String fileName = "shiro_base_auth.ini";
        ClassPathResource cp = new ClassPathResource(fileName);
        INI4j ini = null;
        try {
            ini = new INI4j(cp.getFile());
        } catch (IOException e) {
            LoggerUtils.fmtError(getClass(), e, "加载文件出错。file:[%s]", fileName);
        }
        String section = "base_auth";
        Set<String> keys = ini.get(section).keySet();
        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            String value = ini.get(section, key);
            sb.append(key).append(" = ")
                    .append(value).append(CRLF);
        }

        return sb.toString();

    }

    /**
     * <p>此方法加同步锁</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2019/1/3 14:38
     */
    @Override
    public synchronized void reCreateFilterChains() {
//		ShiroFilterFactoryBean shiroFilterFactoryBean = (ShiroFilterFactoryBean) SpringContextUtil.getBean("shiroFilterFactoryBean");
        AbstractShiroFilter shiroFilter;
        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "getShiroFilter from shiroFilterFactoryBean error!", e);
            throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
        }

        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

        // 清空老的权限控制
        manager.getFilterChains().clear();

        shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
        shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());
        // 重新构建生成
        Map<String, String> chains = shiroFilterFactoryBean
                .getFilterChainDefinitionMap();
        for (Map.Entry<String, String> entry : chains.entrySet()) {
            String url = entry.getKey();
            String chainDefinition = entry.getValue().trim().replace(" ", "");
            manager.createChain(url, chainDefinition);
        }

    }

    public void setShiroFilterFactoryBean(
            ShiroFilterFactoryBean shiroFilterFactoryBean) {
        this.shiroFilterFactoryBean = shiroFilterFactoryBean;
    }

}
