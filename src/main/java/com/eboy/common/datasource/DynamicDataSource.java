package com.eboy.common.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ClassName DynamicDataSource
 * @Description TODO
 * @Author wxj
 * @CreateTime 2019/10/28 14:53
 * @Version 1.0
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
