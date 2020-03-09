package com.eboy.common.util;

import java.util.UUID;

public class LogUtil {

    public static final String REQUEST_ID_HEADER_KEY = "requestid" ;
    public static final String X_FORWARDED_FOR_HEADER_KEY = "X-Forwarded-For" ;
    public static final String REQUEST_APP_NAME_HEADER_KEY = "appname" ;
    public static final String RESPONSE_HOST_HEADER_KEY = "resphost" ;

    public static String getRequestId(){
        return UUID.randomUUID().toString() ;
    }


    public static final String ACCESS_LOG_NAME = "common_access_log" ;


}
