package com.eboy.common.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @ClassName PageList
 * @Description TODO
 * @Author wxj
 * @CreateTime 2020-03-07 18:57
 * @Version 1.0
 **/
public class PageList<E>  extends ArrayList<E> {

    private PageInfo pageInfo;

    public PageList(){

    }

    public PageList(int initialCapacity, PageInfo pageInfo) {
        super(initialCapacity);
        this.pageInfo = pageInfo;
    }

    public PageList(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public PageList(Collection<? extends E> c, PageInfo pageInfo) {
        super(c);
        this.pageInfo = pageInfo;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    @Override
    public String toString() {
        return "{" +
                "\"pageInfo\":" + JSONObject.toJSONString(this.pageInfo) +
                ", \"list\":" + JSONObject.toJSONString(this) +
                "}";
    }
}
