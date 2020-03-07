package com.eboy.common.main;

import com.alibaba.fastjson.JSONObject;
import com.eboy.common.bean.PageInfo;
import com.eboy.common.bean.PageList;

import java.util.ArrayList;

/**
 * @ClassName Test
 * @Description TODO
 * @Author wxj
 * @CreateTime 2020-03-07 19:18
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) {


        PageList<String> pageList = new PageList<String>();
        PageInfo pageInfo = new PageInfo(10,20,100L);
        pageList.setPageInfo(pageInfo);
        pageList.add("aaa");
        pageList.add("aaa");
        pageList.add("aaa");
        pageList.add("aaa");
        ArrayList<PageList> list = new ArrayList<PageList>();
        list.add(pageList);
        System.out.println(JSONObject.toJSONString(list));
    }
}
