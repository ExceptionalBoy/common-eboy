package com.eboy.common.datasource;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName DynamicDattaSourceAspect
 * @Description TODO
 * @Author wxj
 * @CreateTime 2019/10/28 16:16
 * @Version 1.0
 **/
@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {

    private Logger logger = Logger.getLogger(DynamicDataSourceAspect.class);

    @Before("@annotation(targetDataSource)")
    public void before(JoinPoint joinPoint, TargetDataSource targetDataSource){
        String sourceName = targetDataSource.value();
        if(!DynamicDataSourceContextHolder.isContainsDataSourceType(sourceName)){
            logger.error("数据源<" + sourceName + ">不存在！");
        }else{
            logger.info("切换数据源<" + sourceName + ">");
            DynamicDataSourceContextHolder.setDataSourceType(sourceName);
        }
    }

    @After("@annotation(targetDataSource)")
    public void after(JoinPoint joinPoint, TargetDataSource targetDataSource){
        logger.info("清除数据源...");
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}
