package com.sojson.common.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>String工具
 * 主要对 StringUtils 的一些方法进行重写,达到更方便的使用</p>
 *
 * @author kqyang
 * @version 1.0
 * @date 2019/1/17 11:53
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

    /**
     * <p>一次性判断多个或单个对象为空</p>
     *
     * @param objects
     * @return 只要有一个元素为Blank，则返回true
     * @author kqyang
     * @version 1.0
     * @date 2019/1/17 11:54
     */
    public static boolean isBlank(Object... objects) {
        Boolean result = false;
        for (Object object : objects) {
            if (null == object || "".equals(object.toString().trim())
                    || "null".equals(object.toString().trim())) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static String getRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val.toLowerCase();
    }

    /**
     * <p>一次性判断多个或单个对象不为空</p>
     *
     * @param objects
     * @return 只要有一个元素不为Blank，则返回true
     * @author kqyang
     * @version 1.0
     * @date 2019/1/17 14:27
     */
    public static boolean isNotBlank(Object... objects) {
        return !isBlank(objects);
    }

    /**
     * <p>一次性判断多个或单个字符串为空</p>
     *
     * @param strings
     * @return 只要有一个元素为Blank，则返回true
     * @author kqyang
     * @version 1.0
     * @date 2019/1/17 14:28
     */
    public static boolean isBlank(String... strings) {
        String[] string = strings;
        return isBlank(string);
    }

    /**
     * <p>一次性判断多个或单个字符串不为空</p>
     *
     * @param strings
     * @return 只要有一个元素不为Blank，则返回true
     * @author kqyang
     * @version 1.0
     * @date 2019/1/17 14:31
     */
    public static boolean isNotBlank(String... strings) {
        String[] string = strings;
        return !isBlank(string);
    }

    /**
     * <p>判断单个字符串为空</p>
     *
     * @param string
     * @return 只要元素为Blank，则返回true
     * @author kqyang
     * @version 1.0
     * @date 2019/1/17 14:34
     */
    public static boolean isBlank(String string) {
        Object object = string;
        return isBlank(object);
    }

    /**
     * <p>判断单个字符串不为空</p>
     *
     * @param string
     * @return 只要元素不为Blank，则返回true
     * @author kqyang
     * @version 1.0
     * @date 2019/1/17 14:37
     */
    public static boolean isNotBlank(String string) {
        Object object = string;
        return !isBlank(object);
    }

    /**
     * <p>判断一个字符串在数组中存在几个</p>
     *
     * @param baseStr
     * @param strings
     * @author kqyang
     * @version 1.0
     * @date 2019/1/21 16:52
     */
    public static int indexOf(String baseStr, String[] strings) {
        if (null == baseStr || baseStr.length() == 0 || null == strings) {
            return 0;
        }
        int i = 0;
        for (String string : strings) {
            boolean result = baseStr.equals(string);
            i = result ? ++i : i;
        }
        return i;
    }

    /**
     * <p>判断一个字符串是否为JSONObject,是返回JSONObject,不是返回null</p>
     *
     * @param args
     * @author kqyang
     * @version 1.0
     * @date 2019/1/21 16:54
     */
    public static net.sf.json.JSONObject isJSONObject(String args) {
        net.sf.json.JSONObject result = null;
        if (isBlank(args)) {
            return result;
        }
        try {
            return net.sf.json.JSONObject.fromObject(args.trim());
        } catch (Exception e) {
            return result;
        }
    }

    /**
     * <p>判断一个字符串是否为JSONArray,是返回JSONArray,不是返回null</p>
     *
     * @param args
     * @author kqyang
     * @version 1.0
     * @date 2019/1/21 16:56
     */
    public static net.sf.json.JSONArray isJSONArray(Object args) {
        JSONArray result = new JSONArray();
        if (isBlank(args)) {
            return null;
        }
        if (args instanceof net.sf.json.JSONArray) {

            net.sf.json.JSONArray arr = (net.sf.json.JSONArray) args;
            for (Object json : arr) {
                if (json != null && json instanceof net.sf.json.JSONObject) {
                    result.add(json);
                    continue;
                } else {
                    result.add(JSONObject.fromObject(json));
                }
            }
            return result;
        } else {
            return null;
        }

    }

    /**
     * <p>字符串去空格</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2019/1/21 16:58
     */
    public static String trimToEmpty(String str) {
        return (isBlank(str) ? "" : str.toString().trim());
    }

    /**
     * <p>将 Strig  进行 BASE64 编码</p>
     *
     * @param str [要编码的字符串]
     * @param bf  [true|false,true:去掉结尾补充的'=',false:不做处理]
     * @author kqyang
     * @version 1.0
     * @date 2019/1/21 16:58
     */
    public static String getBASE64(String str, boolean... bf) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String base64 = new sun.misc.BASE64Encoder().encode(str.getBytes());
        //去掉 '='
        if (isBlank(bf) && bf[0]) {
            base64 = base64.replaceAll("=", "");
        }
        return base64;
    }

    /**
     * <p>将 BASE64 编码的字符串 s 进行解码</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2019/1/21 17:07
     */
    public static String getStrByBASE64(String s) {
        if (isBlank(s)) {
            return "";
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * <p>把Map转换成get请求参数类型,如 {"name"=20,"age"=30} 转换后变成 name=20&age=30</p>
     *
     * @param map
     * @author kqyang
     * @version 1.0
     * @date 2019/1/21 17:12
     */
    public static String mapToGet(Map<? extends Object, ? extends Object> map) {
        String result = "";
        if (map == null || map.size() == 0) {
            return result;
        }
        Set<? extends Object> keys = map.keySet();
        for (Object key : keys) {
            result += ((String) key + "=" + (String) map.get(key) + "&");
        }

        return isBlank(result) ? result : result.substring(0, result.length() - 1);
    }

    /**
     * <p>把一串参数字符串,转换成Map 如"?a=3&b=4" 转换为Map{a=3,b=4}</p>
     *
     * @param args
     * @author kqyang
     * @version 1.0
     * @date 2019/1/21 17:16
     */
    public static Map<String, ? extends Object> getToMap(String args) {
        if (isBlank(args)) {
            return null;
        }
        args = args.trim();
        //如果是?开头,把?去掉
        if (args.startsWith("?")) {
            args = args.substring(1, args.length());
        }
        String[] argsArray = args.split("&");

        Map<String, Object> result = new HashMap<>(16);
        for (String ag : argsArray) {
            if (!isBlank(ag) && ag.indexOf("=") > 0) {

                String[] keyValue = ag.split("=");
                //如果value或者key值里包含 "="号,以第一个"="号为主 ,如  name=0=3  转换后,{"name":"0=3"}, 如果不满足需求,请勿修改,自行解决.

                String key = keyValue[0];
                String value = "";
                for (int i = 1; i < keyValue.length; i++) {
                    value += keyValue[i] + "=";
                }
                value = value.length() > 0 ? value.substring(0, value.length() - 1) : value;
                result.put(key, value);
            }
        }
        return result;
    }

    /**
     * <p>转换成Unicode</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2019/1/21 20:07
     */
    public static String toUnicode(String str) {
        String[] as = new String[str.length()];
        String s1 = "";
        for (int i = 0; i < str.length(); i++) {
            int v = str.charAt(i);
            if (v >= 19968 && v <= 171941) {
                as[i] = Integer.toHexString(str.charAt(i) & 0xffff);
                s1 = s1 + "\\u" + as[i];
            } else {
                s1 = s1 + str.charAt(i);
            }
        }
        return s1;
    }

    /**
     * <p>合并数据</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2019/1/22 12:00
     */
    public static String merge(Object... v) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < v.length; i++) {
            sb.append(v[i]);
        }
        return sb.toString();
    }

    /**
     * <p>字符串转urlcode</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2019/1/22 12:00
     */
    public static String strToUrlcode(String value) {
        try {
            value = java.net.URLEncoder.encode(value, "utf-8");
            return value;
        } catch (UnsupportedEncodingException e) {
            LoggerUtils.error(StringUtils.class, "字符串转换为URLCode失败,value:" + value, e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>urlcode转字符串</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2019/1/22 12:01
     */
    public static String urlcodeToStr(String value) {
        try {
            value = java.net.URLDecoder.decode(value, "utf-8");
            return value;
        } catch (UnsupportedEncodingException e) {
            LoggerUtils.error(StringUtils.class, "URLCode转换为字符串失败;value:" + value, e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>判断字符串是否包含汉字</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2019/1/22 12:02
     */
    public static Boolean containsCN(String txt) {
        if (isBlank(txt)) {
            return false;
        }
        for (int i = 0; i < txt.length(); i++) {
            String bb = txt.substring(i, i + 1);
            boolean cc = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", bb);
            if (cc) {
                return cc;
            }
        }
        return false;
    }

    /**
     * <p>去掉HTML代码</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2019/1/22 14:13
     */
    public static String removeHtml(String news) {
        String s = news.replaceAll("amp;", "").replaceAll("<", "<").replaceAll(">", ">");

        Pattern pattern = Pattern.compile("<(span)?\\sstyle.*?style>|(span)?\\sstyle=.*?>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(s);
        String str = matcher.replaceAll("");

        Pattern pattern2 = Pattern.compile("(<[^>]+>)", Pattern.DOTALL);
        Matcher matcher2 = pattern2.matcher(str);
        String strhttp = matcher2.replaceAll(" ");


        String regEx = "(((http|https|ftp)(\\s)*((\\:)|：))(\\s)*(//|//)(\\s)*)?"
                + "([\\sa-zA-Z0-9(\\.|．)(\\s)*\\-]+((\\:)|(:)[\\sa-zA-Z0-9(\\.|．)&%\\$\\-]+)*@(\\s)*)?"
                + "("
                + "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])"
                + "|([\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*)*[\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*[\\sa-zA-Z]*"
                + ")"
                + "((\\s)*(\\:)|(：)(\\s)*[0-9]+)?"
                + "(/(\\s)*[^/][\\sa-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*";
        Pattern p1 = Pattern.compile(regEx, Pattern.DOTALL);
        Matcher matchhttp = p1.matcher(strhttp);
        String strnew = matchhttp.replaceAll("").replaceAll("(if[\\s]*\\(|else|elseif[\\s]*\\().*?;", " ");


        Pattern patterncomma = Pattern.compile("(&[^;]+;)", Pattern.DOTALL);
        Matcher matchercomma = patterncomma.matcher(strnew);
        String strout = matchercomma.replaceAll(" ");
        String answer = strout.replaceAll("[\\pP‘’“”]", " ")
                .replaceAll("\r", " ").replaceAll("\n", " ")
                .replaceAll("\\s", " ").replaceAll("　", "");


        return answer;
    }

    /**
     * <p>把数组的空数据去掉</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2019/1/22 14:14
     */
    public static List<String> array2Empty(String[] array) {
        List<String> list = new ArrayList<>(32);
        for (String string : array) {
            if (StringUtils.isNotBlank(string)) {
                list.add(string);
            }
        }
        return list;
    }

    /**
     * <p>把数组转换成set</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2019/1/22 14:15
     */
    public static Set<?> array2Set(Object[] array) {
        Set<Object> set = new TreeSet<>();
        for (Object id : array) {
            if (null != id) {
                set.add(id);
            }
        }
        return set;
    }

    /**
     * <p>serializable toString</p>
     *
     * @author kqyang
     * @version 1.0
     * @date 2019/1/22 14:15
     */
    public static String toString(Serializable serializable) {
        if (null == serializable) {
            return null;
        }
        try {
            return (String) serializable;
        } catch (Exception e) {
            return serializable.toString();
        }
    }
}