package com.eboy.common.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @ClassName PageList
 * @Description TODO
 * @Author wxj
 * @CreateTime 2020-03-07 18:57
 * @Version 1.0
 **/
public class PageList<E> implements Serializable {

    private PageInfo pageInfo;

    private ArrayList<E> list;

    public PageList(){

    }

    public PageList(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public PageList(ArrayList<E> list) {
        this.list = list;
    }

    public PageList(PageInfo pageInfo, ArrayList<E> list) {
        this.pageInfo = pageInfo;
        this.list = list;
    }
}
