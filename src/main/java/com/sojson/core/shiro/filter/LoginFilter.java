package com.sojson.core.shiro.filter;

import com.sojson.common.model.UUser;
import com.sojson.common.utils.LoggerUtils;
import com.sojson.core.shiro.token.manager.TokenManager;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>判断登录</p>
 * <p>
 * <p>
 *     这个类才是限制应用中的资源能否被访问的filter，我们先看的onPreHandle方法：<br/>
 *     publicboolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
 *       return isAccessAllowed(request, response, mappedValue) || onAccessDenied(request, response, mappedValue);
 *     }
 *     可以发现他是调用的isAccessAllowed方法和onAccessDenied方法，只要两者有一个可以就可以了，从名字中我们也可以理解，他的逻辑是这样：
 *     先调用isAccessAllowed，如果返回的是true，则直接放行执行后面的filter和servlet，如果返回的是false，则继续执行后面的onAccessDenied方法，
 *     如果后面返回的是true则也可以有权限继续执行后面的filter和servelt。
 *     只有两个函数都返回false才会阻止后面的filter和servlet的执行。
 *     isAccessAllowed方法在这个类中都是抽象的，依靠实现类实现。onAccessDenied方法不是抽象的，但是调用了另一个抽象的方法：
 *     org.apache.shiro.web.filter.AccessControlFilter.onAccessDenied(ServletRequest, ServletResponse)
 *     这个方法忽略了之前配置的param参数。
 * </p>
 *
 * @author kqyang
 * @version 1.0
 * @date 2019/1/3 15:23
 */
public class LoginFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        UUser token = TokenManager.getToken();
        // && isEnabled()
        if (null != token || isLoginRequest(request, response)) {
            return Boolean.TRUE;
        }
        // ajax请求
        if (ShiroFilterUtils.isAjax(request)) {
            Map<String, String> resultMap = new HashMap<>(16);
            LoggerUtils.debug(getClass(), "当前用户没有登录，并且是Ajax请求！");
            resultMap.put("login_status", "300");
            // 当前用户没有登录！
            resultMap.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");
            ShiroFilterUtils.out(response, resultMap);
        }
        return Boolean.FALSE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        // 保存Request和Response 到登录后的链接
        saveRequestAndRedirectToLogin(request, response);

        return Boolean.FALSE;
    }
}
