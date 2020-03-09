package com.eboy.common.datasource;

import java.lang.annotation.*;

/**
 * @ClassName DynamicDataSourceAnnotation
 * @Description TODO
 * @Author wxj
 * @CreateTime 2019/10/28 16:11
 * @Version 1.0
 **/
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {

    String value() default "master";

}
