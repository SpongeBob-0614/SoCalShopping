package com.spongebob.socalshopping.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Collections;

@Service
@Slf4j
public class RedisService {

    @Resource
    private JedisPool jedisPool;

    public void setValue(String redisKey, long value) {
        Jedis resource = jedisPool.getResource();
        resource.set(redisKey, String.valueOf(value));
        resource.close();
    }

    public void setValue(String redisKey, String value) {
        Jedis resource = jedisPool.getResource();
        resource.set(redisKey, value);
        resource.close();
    }

    public void getValue(String redisKey) {
        Jedis resource = jedisPool.getResource();
        resource.get(redisKey);
        resource.close();
    }

    public long deductStock(String redisKey){
        Jedis resource = jedisPool.getResource();
        //lua scripts
        String scripts = "if redis.call('exists', KEYS[1]) == 1 then\n" +
                " local stock = tonumber(redis.call('get', KEYS[1]))\n" +
                " if (stock<=0) then\n" +
                " return -1\n" +
                " end\n" +
                "\n" +
                " redis.call('decr', KEYS[1]);\n" +
                " return stock - 1;\n" +
                "end\n" +
                "\n" +
                "return -1;";
//        Long stockCount = (Long) resource.eval(scripts, List.of(redisKey), List.of(null));//java 9
        Long stockCount = (Long)resource.eval(scripts, Collections.singletonList(redisKey), Collections.emptyList());
        resource.close();
        if(stockCount < 0){
            log.info("There is no stock available");
            return -1;
        }else {
            return stockCount;
        }
    }

    public Boolean tryDistributedLock(String lockKey, String requestId, int expireTime) {
        Jedis resource = jedisPool.getResource();
        //NX: 发现已经存在 返回Null
        //PX: 有ParamDate
        String result = resource.set(lockKey, requestId, "NX", "PX", expireTime);
        resource.close();
        if("OK".equals(result)){
            return true;
        }
        return false;
    }

    public Boolean releaseLock(String lockKey, String requestId){
        Jedis resource = jedisPool.getResource();
        String scripts =   "if redis.call('get', KEYS[1]) == ARGV[1]" +
                " then return redis.call('del', KEYS[1])" +
                " else return 0 end";
        Long result = (Long) resource.eval(scripts, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        if(result == 1L){
            return true;
        }
        return false;

    }

    public void revertStock(String redisKey) {
        Jedis resource = jedisPool.getResource();
        resource.incr(redisKey);
        resource.close();
    }

    public void addToDenyList(Long userId, Long commodityId){
        Jedis resource = jedisPool.getResource();
        String key = "denyList:"+userId;
        resource.sadd(key, String.valueOf(commodityId));
        resource.close();
        log.info("Add userId: {} into denyList for commodityId: {}", userId, commodityId);
    }

    public void removeToDenyList(Long userId, Long commodityId){
        Jedis resource = jedisPool.getResource();
        String key = "denyList:"+userId;
        resource.srem(key, String.valueOf(commodityId));
        resource.close();
        log.info("Remove userId: {} into denyList for commodityId: {}", userId, commodityId);
    }

    public boolean isInDenyList(Long userId, Long commodityId){
        Jedis resource = jedisPool.getResource();
        String key = "denyList:"+userId;
        Boolean isInDenyList = resource.sismember(key, String.valueOf(commodityId));
        resource.close();
        log.info("userId: {}, commodityId {} is InDenyList result: {}", userId, commodityId, isInDenyList);
        return isInDenyList;
    }
}

