package com.eboy.common.datasource;

import org.apache.log4j.Logger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DynamicDataSourceRegister
 * @Description TODO
 * @Author wxj
 * @CreateTime 2019/10/28 14:58
 * @Version 1.0
 **/
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Logger logger = Logger.getLogger(DynamicDataSourceRegister.class);

    private static final String DEFAULT_DATA_SOURCE_TYPE = "com.zaxxer.hikari.HikariDataSource";

    private static final String DEFAULT_MASTER_DATA_SOURCE_CONFIG_PREFIX = "dynamic.datasource.master.";

    private static final String DEFAULT_SLAVE_DATA_SOURCE_CONFIG_PREFIX = "dynamic.datasource.slave.";

    private DataSource defaultDataSource;

    private Map<String,DataSource> slaveDataSources = new HashMap<String,DataSource>();

    @Override
    public void setEnvironment(Environment environment) {
        initMasterDataSource(environment);
        initSlaveDataSource(environment);
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<Object,Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put("dataSource",this.defaultDataSource);
        DynamicDataSourceContextHolder.dataSources.add("dataSource");
        targetDataSources.putAll(this.slaveDataSources);
        this.slaveDataSources.keySet().forEach(key->{
            DynamicDataSourceContextHolder.dataSources.add(key);
        });

        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(DynamicDataSource.class);
        genericBeanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = genericBeanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource",defaultDataSource);
        mpv.addPropertyValue("targetDataSources",targetDataSources);

        beanDefinitionRegistry.registerBeanDefinition("dataSource",genericBeanDefinition);

    }

    /**
     * @MethodName initMasterDataSource
     * @Description  TODO
     * @Author ExceptionalBoy
     * @Date 2019/10/28 15:49
     *
     * @Param [env]
     * @return void
     **/
    private void initMasterDataSource(Environment env){
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("driver",env.getProperty(DEFAULT_MASTER_DATA_SOURCE_CONFIG_PREFIX + "driver"));
        paramsMap.put("url",env.getProperty(DEFAULT_MASTER_DATA_SOURCE_CONFIG_PREFIX + "url"));
        paramsMap.put("username",env.getProperty(DEFAULT_MASTER_DATA_SOURCE_CONFIG_PREFIX + "username"));
        paramsMap.put("password",env.getProperty(DEFAULT_MASTER_DATA_SOURCE_CONFIG_PREFIX + "password"));
        defaultDataSource = buildDataSource(paramsMap);
    }

    /**
     * @MethodName initSlaveDataSource
     * @Description  TODO
     * @Author ExceptionalBoy
     * @Date 2019/10/28 15:49
     *
     * @Param [env]
     * @return void
     **/
    private void initSlaveDataSource(Environment env){
        Object list = env.getProperty(DEFAULT_SLAVE_DATA_SOURCE_CONFIG_PREFIX + "list");
        if(null != list){
           Arrays.asList(list.toString().split(",")).forEach(element->{
               Map<String,Object> paramsMap = new HashMap<String,Object>();
               paramsMap.put("driver",env.getProperty(DEFAULT_SLAVE_DATA_SOURCE_CONFIG_PREFIX + element + ".driver"));
               paramsMap.put("url",env.getProperty(DEFAULT_SLAVE_DATA_SOURCE_CONFIG_PREFIX + element + ".url"));
               paramsMap.put("username",env.getProperty(DEFAULT_SLAVE_DATA_SOURCE_CONFIG_PREFIX + element + ".username"));
               paramsMap.put("password",env.getProperty(DEFAULT_SLAVE_DATA_SOURCE_CONFIG_PREFIX + element + ".password"));
               slaveDataSources.put(element,buildDataSource(paramsMap));
           });
        }

    }


    
    /**
     * @MethodName buildDataSource
     * @Description  TODO
     * @Author ExceptionalBoy
     * @Date 2019/10/28 15:27
     *
     * @Param [params]
     * @return javax.sql.DataSource 
     **/
    private DataSource buildDataSource(Map<String,Object> params){

        Object type = params.get("type");
        if(null == type){
            type = DEFAULT_DATA_SOURCE_TYPE;
        }
        DataSource dataSource = null;
        Class<? extends DataSource> dataSourceType = null;
        try {
            dataSourceType = (Class<? extends DataSource>)Class.forName(type.toString());
            String driver = params.get("driver").toString();
            String url = params.get("url").toString();
            String username = params.get("username").toString();
            String password = params.get("password").toString();
            dataSource = DataSourceBuilder.create()
                    .driverClassName(driver)
                    .url(url)
                    .username(username)
                    .password(password)
                    .type(dataSourceType)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;

    }
}
