package com.sojson.core.statics;

import com.sojson.core.config.IConfig;

import java.util.Calendar;

/**
 * <p>静态变量</p>
 *
 * @author kqyang
 * @version 1.0
 * @date 2019/1/25 17:01
 */
public interface Constant {

    /**
     * 项目根路径
     */
    String CONTEXT_PATH = "contextPath";

    /***Freemarker 使用的变量 begin**/

    /**
     * 标签使用目标
     */
    String TARGET = "target";
    /**
     * //输出标签Name
     */
    String OUT_TAG_NAME = "outTagName";

    /***Freemarker 使用的变量 end**/


    /**
     * 其他常用变量 begin
     **/
    String NAME = "name";
    String ID = "id";
    String TOKEN = "token";
    String LOING_USER = "loing_user";
    /**
     * Long
     */
    Long ZERO = new Long(0);
    Long ONE = new Long(1);
    Long TWO = new Long(2);
    Long THREE = new Long(3);
    Long EIGHT = new Long(8);

    /**
     * String
     */
    String S_ZERO = "0";
    String S_ONE = "1";
    String S_TOW = "2";
    String S_THREE = "3";

    /**
     * Integer
     */
    Integer I_ZERO = 0;
    Integer I_ONE = 1;
    Integer I_TOW = 2;
    Integer I_THREE = 3;
    /**其他常用变量 end**/

    /**
     * cache常用变量 begin
     **/
    String CACHE_NAME = "shiro_cache";
    String CACHE_MANAGER = "cacheManager";
    /**cache常用变量 end**/

    /**
     * 当前年份
     **/
    int NOW_YEAY = Calendar.getInstance().get(Calendar.YEAR);


    /**
     * 地址
     **/
    /**
     * 前端域名
     */
    String DOMAIN_WWW = IConfig.get("domain.www");
    /**
     * 静态资源域名
     */
    String DOMAIN_CDN = IConfig.get("domain.cdn");
    /**
     * 版本号，重启的时间
     */
    String VERSION = String.valueOf(System.currentTimeMillis());

    // 存储到缓存，标识用户的禁止状态，解决在线用户踢出的问题
    String EXECUTE_CHANGE_USER = "SOJSON_EXECUTE_CHANGE_USER";
}