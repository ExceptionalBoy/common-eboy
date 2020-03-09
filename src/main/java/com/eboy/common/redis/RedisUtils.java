package com.eboy.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;
import redis.clients.jedis.params.SetParams;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @ClassName RedisUtil
 * @Description TODO
 * @Author wxj
 * @CreateTime 2020-01-16 11:39
 * @Version 1.0
 **/
@Component
public class RedisUtils {

    private static JedisPool pool;

    @Autowired
    private JedisPool jedisPool;

    private @PostConstruct void init(){
        this.pool = jedisPool;
    }

    
    public static long incrString(String key) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            Response<Long> res = t.incr(key);
            t.exec();
            return res.get();
        } catch (Exception err) {
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static Long setnx(String key,String value) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            Response<Long> res = t.setnx(key,value);
            t.exec();
            return res.get();
        } catch (Exception err) {
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }


    public static void setString(String key, String value, int seconds) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            if (seconds > 0) {
                t.setex(key, seconds, value);
            } else {
                t.set(key, value);
            }
            checkResult(t.exec());
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static void setString(byte[] key, byte[] value, int seconds) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            if (seconds > 0) {
                t.setex(key, seconds, value);
            } else {
                t.set(key, value);
            }
            checkResult(t.exec());
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static void setString(Map<String, String> maps) {
        setString(maps, 0);
    }

    /**
     * 批量保存String类型，保存String类型，将字符串值 value 关联到 key<br> 内部开启Redis事务，同时监控key是否在事务外被修改，如修改事务将回滚
     *
     * @param maps    key,value
     * @param seconds 有效期 秒
     */
    public static void setString(Map<String, String> maps, int seconds) {
        if (maps == null || maps.keySet().size() == 0) {
            throw new NullPointerException();
        }

        Jedis jedis = null;
        Pipeline p = null;
        try {
            jedis = pool.getResource();
            String[] keys = new String[maps.keySet().size()];
            jedis.watch(maps.keySet().toArray(keys));
            p = jedis.pipelined();
            p.multi();
            for (String key : maps.keySet()) {
                if (seconds > 0) {
                    p.setex(key, seconds, maps.get(key));
                } else {
                    p.set(key, maps.get(key));
                }
            }

            p.exec();
            checkResult(p.syncAndReturnAll());
        } catch (Exception err) {
            err.printStackTrace();
            p.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 返回 key 所关联的字符串值
     */
    public static String getString(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 查询key是否存在
     */
    public static boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.exists(key);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static void del(String key) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            t.del(key);
            t.exec();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static void del(Set<String> keys) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            for (String key : keys) {
                jedis.watch(key);
                t = jedis.multi();
                t.del(key);
                t.exec();
            }
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }


    public static List<Object> lpush(String key, String... values) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            t.lpush(key, values);
            List<Object> exec = t.exec();
            return exec;
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static void lpushList(String key,List list) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            for (Object obj : list) {
                t.lpush(key, obj.toString());
            }
            t.exec();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static String brpoplpush(String soruce,String destination,int time) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpoplpush(soruce, destination, time);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static void rpush(String key, String... values) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            t.rpush(key, values);
            t.exec();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }


    public static String lpop(String key) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            Response<String> res = t.lpop(key);
            t.exec();
            return res.get();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static String rpop(String key) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            Response<String> res = t.rpop(key);
            t.exec();
            return res.get();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 分页返回List
     *
     * @param start 0:头部第一个;>0:从头部开始索引;-1:尾部最后一个;<-1:从尾部开始索引
     * @param stop  不能小于start
     */
    public static List<String> lrange(String key, int start, int stop) {
        if (key != null && !"".equals(key)) {
            throw new NullPointerException();
        }
        if (stop < start) {
            throw new IndexOutOfBoundsException("end not less than start");
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lrange(key, start, stop);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }


    /**
     * 删除指定value 所有的key
     * @param key    key
     * @param value
     */
    public static void lrem(String key,String value) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            t.lrem(key, 0, value);
            t.exec();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 返回list中元素个数
     */
    public static long llen(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.llen(key);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static long sortedSetAdd(String key, int index, String value) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            Response<Long> res = t.zadd(key, index, value);
            t.exec();
            return res.get();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static long sortedSetAdd(String key, Map<String, Double> maps) {
        if (maps == null || maps.keySet().size() == 0) {
            throw new NullPointerException();
        }

        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            Response<Long> res = t.zadd(key, maps);
            t.exec();
            return res.get();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static Set<String> sortedSetRange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrange(key, start, end);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static Set<String> sortedSetRangeByScore(String key, int min, int max) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrangeByScore(key, min, max);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static long sortedSetSize(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zcard(key);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static boolean sortedSetExists(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long res = jedis.zrank(key, value);
            return res != null;
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }


    public static long sortedSetRemove(String key, String value) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            Response<Long> res = t.zrem(key, value);
            t.exec();
            return res.get();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static long setAdd(String key, String... values) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            Response<Long> res = t.sadd(key, values);
            t.exec();
            return res.get();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static Set<String> setMembers(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.smembers(key);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static Set<String> keysLike(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.keys(key);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static long setSize(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static boolean setExists(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static long setRemove(String key, String value) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            Response<Long> res = t.srem(key, value);
            t.exec();
            return res.get();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 移除并返回集合中的一个随机元素。<br> 内部开启Redis事务，同时监控key是否在事务外被修改，如修改事务将回滚
     */
    public static String setPop(String key) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            Response<String> res = t.spop(key);
            t.exec();
            return res.get();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 将 key中的field的值设为 value 。<br> 内部开启Redis事务，同时监控key是否在事务外被修改，如修改事务将回滚
     *
     * @param field 字段名
     * @param value 字段值
     */
    public static void hashPut(String key, String field, String value) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            t.hset(key, field, value);
            t.exec();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 同时将多个 hashKey-value (域-值)对设置到哈希表 key 中。<br> 内部开启Redis事务，同时监控key是否在事务外被修改，如修改事务将回滚
     *
     * @param maps 字段名：字段值
     */
    public static void hashPut(String key, Map<String, String> maps) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            t.hmset(key, maps);
            t.exec();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
     */
    public static long hashDelete(String key, String field) {
        Jedis jedis = null;
        Transaction t = null;
        try {
            jedis = pool.getResource();
            jedis.watch(key);
            t = jedis.multi();
            Response<Long> res = t.hdel(key, field);
            t.exec();
            return res.get();
        } catch (Exception err) {
            err.printStackTrace();
            t.discard();
            t.discard();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 返回哈希表 key 中，所有的域和值。
     */
    public static Map<String, String> hashEntries(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hgetAll(key);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 返回哈希表 key 中给定域 field 的值。
     */
    public static String hashGet(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hget(key, field);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 返回哈希表 key 中，一个或多个给定域的值。
     */
    public static List<String> hashGet(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hmget(key, fields);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 查询Hash对象全部字段
     */
    public static Set<String> hashKeys(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hkeys(key);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    public static Set<byte[]> hashKeys(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hkeys(key);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 查询Hash对象全部字段值
     */
    public static List<String> hashValues(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hvals(key);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 检查Hash对象字段是否存在
     */
    public static boolean hasKeyExists(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hexists(key, field);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 返回哈希表 key 中域的数量。
     */
    public static long hashSize(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hlen(key);
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        } finally {
            jedis.close();
        }
    }

    /**
     * 设置 list
     */
    public static <T> void setList(String key, List<T> list) {
        try {
            setString(key.getBytes(), SerializeUtils.serialize(list), 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static List removeDuplicate(List list) {
        if(list.size()<=1){
            return list;
        }
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    /**
     * 获取list
     *
     * @return list
     */
    public static <T> List<T> getList(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null || !jedis.exists(key.getBytes())) {
                return null;
            }
            byte[] in = jedis.get(key.getBytes());
            List<T> list = (List<T>) SerializeUtils.deserialize(in);
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            jedis.close();
        }
    }

    /**
     * 获取map
     *
     * @param key turn list
     */
    public static <T> Map getMap(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null || !jedis.exists(key.getBytes())) {
                return null;
            }
            byte[] in = jedis.get(key.getBytes());
            Map map = (Map) SerializeUtils.deserialize(in);
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            jedis.close();
        }
    }

    private static void checkResult(List<Object> list) {
        if (list == null) {
            throw new RuntimeException("操作失败");
        }
        Object obj = list.get(list.size() - 1);
        if (obj instanceof ArrayList) {
            @SuppressWarnings("unchecked")
            List<String> tempList = (List<String>) obj;
            for (String s : tempList) {
                if (!s.equalsIgnoreCase("ok")) {
                    throw new RuntimeException(s);
                }
            }
        } else {
            if (!obj.toString().equalsIgnoreCase("ok")) {
                throw new RuntimeException(obj.toString());
            }
        }
    }



    /**
     * @return java.lang.Object
     * @methodName getObject 的功能描述：获取对象
     * @throw
     * @author Mr W
     * @create 2017/2/7
     */
    public static <T> Object getObject(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null || !jedis.exists(key.getBytes())) {
                return null;
            }
            byte[] in = jedis.get(key.getBytes());
            Object object = SerializeUtils.deserialize(in);
            return object;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            jedis.close();
        }
    }

    /**
     * 设置超时时间
     * @param key
     * @param time
     */
    public static boolean setExpire(String key,int time) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if(jedis==null){
                return false;
            }
            long re = jedis.expire(key,time);
            if(re>0){
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            jedis.close();
        }
        return false;
    }

    /**
     *  redis分布式锁>>>加锁 阻塞式
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public static void tryLock(String key, String value, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            for (; ; ) {
                //redis.clients 2.9.*实现方法
                //String set = jedis.set(key, value, "NX", "PX", expireTime);
                //redis.clients 3.*.*实现方法
                SetParams params = new SetParams();
                params.nx();
                params.px(expireTime);
                String set = jedis.set(key,value,params);
                if (set != null && "OK".equals(set)) {
                    break;
                }
                Thread.sleep(1000);
            }
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    /**
     *  redis分布式锁>>>加锁
     * @param key
     * @param value
     * @param expireTime 单位毫秒
     */
    public static Boolean lock(String key,String value,int expireTime) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            //redis.clients 2.9.*实现方法
            //String set = jedis.set(key, value, "NX", "PX", expireTime);
            //redis.clients 3.*.*实现方法
            SetParams params = new SetParams();
            params.nx();
            params.px(expireTime);
            String set = jedis.set(key,value,params);
            return set != null && "OK".equals(set);
        } catch (Exception err) {
            err.printStackTrace();
            return false;
        } finally {
            jedis.close();
        }
    }
    /**
     *  redis分布式锁>>>解锁
     * @param key
     * @param value
     */
    public static Boolean unLock(String key,String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
            return Long.valueOf(1).equals(result);
        } catch (Exception err) {
            err.printStackTrace();
            return false;
        } finally {
            jedis.close();
        }
    }
}
