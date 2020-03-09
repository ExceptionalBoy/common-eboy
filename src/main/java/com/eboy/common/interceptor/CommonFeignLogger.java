package com.eboy.common.interceptor;

import com.eboy.common.util.LogUtil;
import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static feign.Util.valuesOrEmpty;
import static java.lang.String.format;

@Configuration
public class CommonFeignLogger {

    @Autowired
    private CommonFeignLogger.FeignLoggerProperties feignLoggerProperties;

    @ConfigurationProperties(prefix = "feign.client")
    @Component
    class FeignLoggerProperties {

        private boolean logDisable;

        private List<String> logDisableServerName;

        public boolean getLogDisable() {
            return logDisable;
        }

        public void setLogDisable(boolean logDisable) {
            this.logDisable = logDisable;
        }

        public List<String> getLogDisableServerName() {
            return logDisableServerName;
        }

        public void setLogDisableServerName(List<String> logDisableServerName) {
            this.logDisableServerName = logDisableServerName;
        }
    }

    @Value("${spring.application.name}")
    private String serverName;

    @Bean
    public Logger.Level setFeignLoggerLevel() {
        return feign.Logger.Level.FULL;
    }

    @Bean
    public Logger setFeignLogger() {
        return new FeignLogger();
    }

    private boolean showLog() {
        if (feignLoggerProperties == null) {
            return Boolean.TRUE;
        } else if (feignLoggerProperties.getLogDisable() && feignLoggerProperties.getLogDisableServerName() != null && !feignLoggerProperties.getLogDisableServerName().isEmpty()) {
            return feignLoggerProperties.getLogDisableServerName().contains(CommonRequest.requestAppName.get()) ?
                    Boolean.FALSE
                    :
                    Boolean.TRUE;

        } else {
            return !feignLoggerProperties.getLogDisable();
        }
    }

    class FeignLogger extends Logger {

        private final org.slf4j.Logger access_log = LoggerFactory.getLogger(LogUtil.ACCESS_LOG_NAME);
        private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm.SSS");

        private static final String NOT_DATA = "-";

        @Override
        protected void log(String configKey, String format, Object... args) {
            if (showLog()) {
                if (logger.isInfoEnabled()) {
                    logger.info(String.format(methodTag(configKey) + format, args));
                }
            }
//            System.out.print(Thread.currentThread().getName() + requestId.get() + configKey);
//            System.out.format(format, args);
//            System.out.println();
        }

        @Override
        protected void logRequest(String configKey, Level logLevel, Request request) {
            //透传url和appName
            CommonRequest.requestUrl.set(request.url());
            CommonRequest.requestAppName.set(getAppNameByRequestUrl(request.url()));
            log(configKey, "---> %s %s HTTP/1.1", request.method(), request.url());
            if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {

//                for (String field : request.headers().keySet()) {
//                    for (String value : valuesOrEmpty(request.headers(), field)) {
//                        log(configKey, "%s: %s", field, value);
//                    }
//                }

                int bodyLength = 0;
                if (request.body() != null) {
                    bodyLength = request.body().length;
                    if (logLevel.ordinal() >= Level.FULL.ordinal()) {
                        String bodyText = request.charset() != null ? new String(request.body(), request.charset())
                                : null;
                        log(configKey, ""); // CRLF
                        log(configKey, "%s", bodyText != null ? bodyText : "Binary data");
                    }
                }
                log(configKey, "---> END HTTP (%s-byte body)", bodyLength);
            }
        }

        private final String getAppNameByRequestUrl(String url) {
            String notSchemaUrl = url.replace("http://", "").replace("https://", "");
            return notSchemaUrl.substring(0, notSchemaUrl.indexOf("/"));
        }

        @Override
        protected void logRetry(String configKey, Level logLevel) {
            log(configKey, "---> RETRYING");
        }

        @Override
        protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime)
                throws IOException {

            String reason = response.reason() != null && logLevel.compareTo(Level.NONE) > 0 ? " " + response.reason()
                    : "";
            int status = response.status();
            log(configKey, "<--- HTTP/1.1 %s%s (%sms)", status, reason, elapsedTime);
            byte[] bodyData = null;
            if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {

//                for (String field : response.headers().keySet()) {
//                    for (String value : valuesOrEmpty(response.headers(), field)) {
//                        log(configKey, "%s: %s", field, value);
//                    }
//                }

                int bodyLength = 0;
                if (response.body() != null && !(status == 204 || status == 205)) {
                    // HTTP 204 No Content "...response MUST NOT include a message-body"
                    // HTTP 205 Reset Content "...response MUST NOT include an entity"
//                    if (logLevel.ordinal() >= Level.FULL.ordinal()) {
//                        log(configKey, ""); // CRLF
//                    }
                    bodyData = Util.toByteArray(response.body().asInputStream());
                    bodyLength = bodyData.length;
//                    if (logLevel.ordinal() >= Level.FULL.ordinal() && bodyLength > 0) {
                    if (bodyLength > 0) {
                        log(configKey, "%s", decodeOrDefault(bodyData, Util.UTF_8, "Binary data"));
                    }
                    log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);

//                    return Response.create(response.status(), response.reason(), response.headers(), bodyData);
                    //return response.toBuilder().body(bodyData).build();
                } else {
                    log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
                }
            }

            String errorMessage = null;
            if (response.status() != 200 && bodyData != null && bodyData.length > 0) {
                errorMessage = new String(bodyData) ;
                errorMessage = errorMessage.length() > 100 ? errorMessage.substring(0,  99)
                        : errorMessage.substring(0,  errorMessage.length() -1 );
            }
            String respHost = CommonRequest.getHaderValue(valuesOrEmpty(response.headers(), LogUtil.RESPONSE_HOST_HEADER_KEY));
            StringBuilder sb = new StringBuilder();
            sb.append(checkStr(CommonRequest.requestAppName.get())).append(" ")
                    .append(checkStr(sdf.format(new Date()))).append(" ")
                    .append(checkStr(InetAddress.getLocalHost().getHostAddress())).append(" ")
                    .append(checkStr(CommonRequest.xForwardedFor.get())).append(" ")
                    .append(checkStr(CommonRequest.requestMethod.get() + " " + CommonRequest.requestUrl.get())).append(" ")
                    .append(checkStr(String.valueOf(response.status()))).append(" ")
                    .append(checkStr(String.valueOf(elapsedTime))).append(" ")
                    .append(checkStr(String.valueOf((response == null || response.body() == null
                            || response.body().length() == null) ? 0 : response.body().length()))).append(" ")
//                    .append(checkStr(String.valueOf(0))).append(" ")
                    .append(checkStr(serverName)).append(" ")
                    .append(checkStr(CommonRequest.requestId.get())).append(" ")
                    .append(checkStr(respHost)).append(" ")
                    .append(checkStr(errorMessage))
            ;
            access_log.info(sb.toString());
            return Response.builder().status(response.status()).reason(response.reason()).
                    headers(response.headers()).body(bodyData).request(response.request()).build();
        }


        public String checkStr(String str) {
            return StringUtils.isEmpty(str) ? "\"" + NOT_DATA + "\"" : "\"" + str.replace("\"", "'") + "\"";
        }

        protected IOException logIOException(String configKey, Level logLevel, IOException ioe, long elapsedTime) {
            log(configKey, "<--- ERROR %s: %s (%sms)", ioe.getClass().getSimpleName(), ioe.getMessage(), elapsedTime);
            if (logLevel.ordinal() >= Level.FULL.ordinal()) {
                StringWriter sw = new StringWriter();
                ioe.printStackTrace(new PrintWriter(sw));
                log(configKey, sw.toString());
                log(configKey, "<--- END ERROR");
            }
            return ioe;
        }

        /**
         * Copy of {@code feign.Util#decodeOrDefault}
         */
        private String decodeOrDefault(byte[] data, Charset charset, String defaultValue) {
            if (data == null) {
                return defaultValue;
            }
            checkNotNull(charset, "charset");
            try {
                return charset.newDecoder().decode(ByteBuffer.wrap(data)).toString();
            } catch (CharacterCodingException ex) {
                return defaultValue;
            }
        }

        /**
         * Copy of {@code com.google.common.base.Preconditions#checkNotNull}.
         */
        public <T> T checkNotNull(T reference,
                                  String errorMessageTemplate,
                                  Object... errorMessageArgs) {
            if (reference == null) {
                // If either of these parameters is null, the right thing happens anyway
                throw new NullPointerException(
                        format(errorMessageTemplate, errorMessageArgs));
            }
            return reference;
        }

    }
}
