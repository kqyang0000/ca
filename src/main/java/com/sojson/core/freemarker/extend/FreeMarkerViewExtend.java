package com.sojson.core.freemarker.extend;

import com.sojson.common.model.UUser;
import com.sojson.common.utils.LoggerUtils;
import com.sojson.core.shiro.token.manager.TokenManager;
import com.sojson.core.statics.Constant;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class FreeMarkerViewExtend extends FreeMarkerView {

    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) {

        try {
            super.exposeHelpers(model, request);
        } catch (Exception e) {
            LoggerUtils.fmtError(FreeMarkerViewExtend.class, e, "FreeMarkerViewExtend 加载父类出现异常。请检查。");
        }
        // ca
        model.put(Constant.CONTEXT_PATH, request.getContextPath());
        model.putAll(Ferrmarker.initMap);
        UUser token = TokenManager.getToken();
        //String ip = IPUtils.getIP(request);
        // 登录的token
        if (TokenManager.isLogin()) {
            model.put("token", token);
        }

        model.put("_time", System.currentTimeMillis());
        // 今年
        model.put("NOW_YEAY", Constant.NOW_YEAY);
        // 版本号，重启的时间
        model.put("_v", Constant.VERSION);
        // CDN域名
        model.put("cdn", Constant.DOMAIN_CDN);
        // base目录。
        model.put("basePath", request.getContextPath());
    }
}
