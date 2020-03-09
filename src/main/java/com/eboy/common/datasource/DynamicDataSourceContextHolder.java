package com.eboy.common.datasource;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DynamicDataSourceContextHolder
 * @Description TODO
 * @Author wxj
 * @CreateTime 2019/10/28 14:42
 * @Version 1.0
 **/
public class DynamicDataSourceContextHolder {

    private Logger logger = Logger.getLogger(DynamicDataSourceContextHolder.class);

    private static final ThreadLocal<String> context = new ThreadLocal<String>();

    public static List<String> dataSources = new ArrayList<String>();

    public static void setDataSourceType(String dataSourceType){
        context.set(dataSourceType);
    }

    public static String getDataSourceType(){
        return context.get();
    }

    public static void clearDataSourceType(){
        context.remove();
    }

    public static boolean isContainsDataSourceType(String dataSourceType){
        return dataSources.contains(dataSourceType);
    }
}
