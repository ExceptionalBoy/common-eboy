package com.eboy.common.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName RedisConfiguration
 * @Description TODO
 * @Author wxj
 * @CreateTime 2019/10/25 9:27
 * @Version 1.0
 **/

@Configuration
public class RedisConfiguration {

   @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private String database;

    @Value("${spring.redis.timeout}")
    private String timeout;

    @Value("${spring.redis.testOnBorrow}")
    private String testOnBorrow;

    @Value("${spring.redis.testOnReturn}")
    private String testOnReturn;

    @Value("${spring.redis.jedis.pool.max-active}")
    private String maxActive;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private String maxWait;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private String minIdle;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private String maxIdle;


    @Bean
    public JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(Integer.parseInt(maxIdle));
        jedisPoolConfig.setMinIdle(Integer.parseInt(minIdle));
        jedisPoolConfig.setMaxTotal(Integer.parseInt(maxActive));
        jedisPoolConfig.setTestOnBorrow(Boolean.parseBoolean(testOnBorrow));
        jedisPoolConfig.setTestOnReturn(Boolean.parseBoolean(testOnReturn));
        jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(maxWait));
        return jedisPoolConfig;
    }

    @Bean
    public JedisPool getJedisPool(JedisPoolConfig jedisPoolConfig){
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,host,Integer.parseInt(port),Integer.parseInt(timeout),password,Integer.parseInt(database));
        return jedisPool;
    }
}
