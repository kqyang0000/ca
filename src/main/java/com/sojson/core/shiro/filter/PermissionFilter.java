package com.sojson.core.shiro.filter;

import com.sojson.common.utils.LoggerUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>权限校验 Filter</p>
 *
 * @author kqyang
 * @version 1.0
 * @date 2019/1/3 16:37
 */
public class PermissionFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        // 先判断带参数的权限判断
        Subject subject = getSubject(request, response);
        if (null != mappedValue) {
            String[] arra = (String[]) mappedValue;
            for (String permission : arra) {
                if (subject.isPermitted(permission)) {
                    return Boolean.TRUE;
                }
            }
        }
        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        /**
         * 此处是改版后，为了兼容项目不需要部署到root下，也可以正常运行，但是权限没设置目前必须到root 的URI，
         * 原因：如果你把这个项目叫 ShiroDemo，那么路径就是 /ShiroDemo/xxxx.shtml ，那另外一个人使用，又叫Shiro_Demo,那么就要这么控制/Shiro_Demo/xxxx.shtml
         * 理解了吗？
         * 所以这里替换了一下，使用根目录开始的URI
         */

        // 获取URI
        String uri = httpRequest.getRequestURI();
        // 获取basePath
        String basePath = httpRequest.getContextPath();
        if (null != uri && uri.startsWith(basePath)) {
            uri = uri.replaceFirst(basePath, "");
        }
        if (subject.isPermitted(uri)) {
            return Boolean.TRUE;
        }
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
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        Subject subject = getSubject(request, response);
        // 表示没有登录，重定向到登录页面
        if (null == subject.getPrincipal()) {
            saveRequest(request);
            WebUtils.issueRedirect(request, response, ShiroFilterUtils.LOGIN_URL);
        } else {
            // 如果有未授权页面跳转过去
            if (StringUtils.hasText(ShiroFilterUtils.UNAUTHORIZED)) {
                WebUtils.issueRedirect(request, response, ShiroFilterUtils.UNAUTHORIZED);
            } else {//否则返回401未授权状态码
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return Boolean.FALSE;
    }
}
