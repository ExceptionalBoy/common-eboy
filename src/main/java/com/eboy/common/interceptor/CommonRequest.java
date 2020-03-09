package com.eboy.common.interceptor;

import org.apache.logging.log4j.util.Strings;

import java.util.Collection;
import java.util.UUID;

public final class CommonRequest {

    protected static ThreadLocal<String> requestId = new ThreadLocal<>();
    protected static ThreadLocal<String> xForwardedFor = new ThreadLocal<>();
    protected static ThreadLocal<String> requestMethod = new ThreadLocal<>();
    protected static ThreadLocal<String> requestUrl = new ThreadLocal<>();
    protected static ThreadLocal<String> requestAppName = new ThreadLocal<>();

    protected static final <T> T getHaderValue(Collection<T> headerCollection) {
        if (headerCollection != null && headerCollection.size() > 0) {
            for (T value : headerCollection) {
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }
    
    public static ThreadLocal<String> getRequsetIdThreadLocal() {
    	return requestId;
    }
    
    public static void setRequestid(String rid) {
    	if(Strings.isNotBlank(rid)) {
    		requestId.set(rid);
    	}else {
    		requestId.set(UUID.randomUUID().toString());
    	}
    }

}
