package com.sojson.common.model;

import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * 开发公司：itboy.net<br/>
 * 版权：itboy.net<br/>
 * <p>
 * <p>
 * 角色{@link URole}和 权限{@link UPermission}中间表
 * <p>
 * <p>
 * <p>
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　周柏成　2016年5月25日 　<br/>
 * <p>
 * *******
 * <p>
 *
 * @author zhou-baicheng
 * @version 1.0, 2016年5月25日 <br/>
 * @email i@itboy.net
 */
public class URolePermission implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * {@link URole.id}
     */
    private Long rid;
    /**
     * {@link UPermission.id}
     */
    private Long pid;

    public URolePermission() {
    }

    public URolePermission(Long rid, Long pid) {
        this.rid = rid;
        this.pid = pid;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}