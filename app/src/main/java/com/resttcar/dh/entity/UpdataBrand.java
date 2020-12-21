package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/5/26.
 */
public class UpdataBrand {
    private String token;
    private List<Integer> goods_ids;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Integer> getGoods_ids() {
        return goods_ids;
    }

    public void setGoods_ids(List<Integer> goods_ids) {
        this.goods_ids = goods_ids;
    }
}
