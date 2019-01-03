package com.sojson.core.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>角色判断校验</p>
 *
 * @author kqyang
 * @version 1.0
 * @date 2019/1/3 15:53
 */
public class RoleFilter extends AccessControlFilter {

    static final String LOGIN_URL = "http://localhost:8090/ca/u/login.shtml";
    static final String UNAUTHORIZED_URL = "http://www.sojson.com/unauthorized.html";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String[] arra = (String[]) mappedValue;

        Subject subject = getSubject(request, response);
        for (String role : arra) {
            if (subject.hasRole("role:" + role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        Subject subject = getSubject(request, response);
        // 表示没有登录，重定向到登录页面
        if (subject.getPrincipal() == null) {
            saveRequest(request);
            WebUtils.issueRedirect(request, response, LOGIN_URL);
        } else {
            //如果有未授权页面跳转过去
            if (StringUtils.hasText(UNAUTHORIZED_URL)) {
                WebUtils.issueRedirect(request, response, UNAUTHORIZED_URL);
            } else {//否则返回401未授权状态码
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }

}
