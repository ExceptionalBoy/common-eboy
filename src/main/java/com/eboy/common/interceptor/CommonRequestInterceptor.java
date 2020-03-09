package com.eboy.common.interceptor;

import com.eboy.common.util.LogUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static feign.Util.valuesOrEmpty;

@Configuration
public class CommonRequestInterceptor implements RequestInterceptor {

    @Value("${spring.application.name}")
    private String serverName;

    @Override
    public void apply(RequestTemplate template) {
        try {
            String xForwardFor = CommonRequest.getHaderValue(valuesOrEmpty(template.request().headers(), LogUtil.X_FORWARDED_FOR_HEADER_KEY)) ;
            if(StringUtils.isEmpty(xForwardFor)){
                xForwardFor = InetAddress.getLocalHost().getHostAddress();
            }else{
                xForwardFor += ","+InetAddress.getLocalHost().getHostAddress() ;
            }
            CommonRequest.xForwardedFor.set(xForwardFor);
            template.header(LogUtil.X_FORWARDED_FOR_HEADER_KEY , CommonRequest.xForwardedFor.get()) ;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        template.header(LogUtil.REQUEST_ID_HEADER_KEY, CommonRequest.requestId.get()) ;
        template.header(LogUtil.REQUEST_APP_NAME_HEADER_KEY, serverName) ;
        CommonRequest.requestMethod.set(template.request().method());

    }

}
