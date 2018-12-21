package com.sigma.redis.service.impl;

import com.sigma.redis.service.IRedisService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.*;

@Service
public class RedisClusterServiceImpl implements IRedisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClusterServiceImpl.class);

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public String get(String key) {
        String value = null;
        try {
            value = jedisCluster.get(key);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
        return value;
    }

    @Override
    public Set<String> getByPre(String pre) {
        Set<String> value = null;
        try {
            value = keys(pre + "*");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return value;
    }

    @Override
    public Set<String> keys(String pattern){
        TreeSet<String> keys = new TreeSet<String>();
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for(String k : clusterNodes.keySet()){
            JedisPool jp = clusterNodes.get(k);
            Jedis connection = jp.getResource();
            try {
                keys.addAll(connection.keys(pattern));
            } catch(Exception e){
                LOGGER.error("Getting keys error: {}", e);
            } finally{
                connection.close();//用完一定要close这个链接！！！
            }
        }
        LOGGER.debug("Keys gotten!");
        return keys;
    }

    @Override
    public String set(String key, String value) {
        try {
            return jedisCluster.set(key,value);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "0";
        }
    }

    @Override
    public String set(String key, String value, int expire) {
        try {
            int time = jedisCluster.ttl(key).intValue() + expire;
            String result = jedisCluster.set(key, value);
            jedisCluster.expire(key, time);
            return result;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "0";
        }
    }

    @Override
    public Long delPre(String key) {
        try {
            Set<String> set = keys(key + "*");
            int result = set.size();
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String keyStr = it.next();
                jedisCluster.del(keyStr);
            }
            return new Long(result);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return 0L;
        }
    }

    @Override
    public Long del(String... keys) {
        try {
            return jedisCluster.del(keys);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return 0L;
        }
    }

    @Override
    public Long append(String key, String str) {
        Long res = null;
        try {
            res = jedisCluster.append(key, str);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return 0L;
        }
        return res;
    }

    @Override
    public Boolean exists(String key) {
        try {
            return jedisCluster.exists(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Long setnx(String key, String value) {
        try {
            return jedisCluster.setnx(key, value);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return 0L;
        }
    }

    @Override
    public String setex(String key, String value, int seconds) {
        String res = null;
        try {
            res = jedisCluster.setex(key, seconds, value);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }

    @Override
    public Long setrange(String key, String str, int offset) {
        try {
            return jedisCluster.setrange(key, offset, str);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return 0L;
        }
    }

    @Override
    public List<String> mget(String... keys) {
        List<String> values = null;
        try {
            values = jedisCluster.mget(keys);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return values;
    }

    @Override
    public String mset(String... keysvalues) {
        String res = null;
        try {
            res = jedisCluster.mset(keysvalues);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return res;
    }

    @Override
    public Long msetnx(String... keysvalues) {
        Long res = 0L;
        try {
            res = jedisCluster.msetnx(keysvalues);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }

    @Override
    public String getset(String key, String value) {
        String res = null;
        try {
            res = jedisCluster.getSet(key, value);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }

    @Override
    public String getrange(String key, int startOffset, int endOffset) {
        String res = null;
        try {
            res = jedisCluster.getrange(key, startOffset, endOffset);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return res;
    }

    @Override
    public Long incr(String key) {
        Long res = null;
        try {
            res = jedisCluster.incr(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }

    @Override
    public Long incrBy(String key, Long integer) {
        Long res = null;
        try {
            res = jedisCluster.incrBy(key, integer);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long decr(String key) {
        Long res = null;
        try {
            res = jedisCluster.decr(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long decrBy(String key, Long integer) {
        Long res = null;
        try {
            res = jedisCluster.decrBy(key, integer);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long serlen(String key) {
        Long res = null;
        try {
            res = jedisCluster.strlen(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long hset(String key, String field, String value) {
        Long res = null;
        try {
            res = jedisCluster.hset(key, field, value);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long hsetnx(String key, String field, String value) {
        Long res = null;
        try {
            res = jedisCluster.hsetnx(key, field, value);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public String hmset(String key, Map<String, String> hash) {
        String res = null;
        try {
            res = jedisCluster.hmset(key, hash);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public String hget(String key, String field) {
        String res = null;
        try {
            res = jedisCluster.hget(key, field);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public List<String> hmget(String key, String... fields) {
        List<String> res = null;
        try {
            res = jedisCluster.hmget(key, fields);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long hincrby(String key, String field, Long value) {
        Long res = null;
        try {
            res = jedisCluster.hincrBy(key, field, value);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Boolean hexists(String key, String field) {
        Boolean res = false;
        try {
            res = jedisCluster.hexists(key, field);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long hlen(String key) {
        Long res = null;
        try {
            res = jedisCluster.hlen(key);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return res;

    }


    @Override
    public Long hdel(String key, String... fields) {
        Long res = null;
        try {
            res = jedisCluster.hdel(key, fields);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Set<String> hkeys(String key) {
        Set<String> res = null;
        try {
            res = jedisCluster.hkeys(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public List<String> hvals(String key) {
        List<String> res = null;
        try {
            res = jedisCluster.hvals(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Map<String, String> hgetall(String key) {
        Map<String, String> res = null;
        try {
            res = jedisCluster.hgetAll(key);
        } catch (Exception e) {
            // TODO
        }
        return res;
    }


    @Override
    public Long lpush(String key, String... strs) {
        Long res = null;
        try {
            res = jedisCluster.lpush(key, strs);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long rpush(String key, String... strs) {
        Long res = null;
        try {
            res = jedisCluster.rpush(key, strs);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
        Long res = null;
        try {
            res = jedisCluster.linsert(key, where, pivot, value);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public String lset(String key, Long index, String value) {
        String res = null;
        try {
            res = jedisCluster.lset(key, index, value);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long lrem(String key, long count, String value) {
        Long res = null;
        try {
            res = jedisCluster.lrem(key, count, value);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public String ltrim(String key, long start, long end) {
        String res = null;
        try {
            res = jedisCluster.ltrim(key, start, end);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    synchronized public String lpop(String key) {
        String res = null;
        try {
            res = jedisCluster.lpop(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    synchronized public String rpop(String key) {
        String res = null;
        try {
            res = jedisCluster.rpop(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public String rpoplpush(String srckey, String dstkey) {
        String res = null;
        try {
            res = jedisCluster.rpoplpush(srckey, dstkey);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public String lindex(String key, long index) {
        String res = null;
        try {
            res = jedisCluster.lindex(key, index);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long llen(String key) {
        Long res = null;
        try {
            res = jedisCluster.llen(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public List<String> lrange(String key, long start, long end) {
        List<String> res = null;
        try {
            res = jedisCluster.lrange(key, start, end);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long sadd(String key, String... members) {
        Long res = null;
        try {
            res = jedisCluster.sadd(key, members);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long srem(String key, String... members) {
        Long res = null;
        try {
            res = jedisCluster.srem(key, members);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public String spop(String key) {
        String res = null;
        try {
            res = jedisCluster.spop(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Set<String> sdiff(String... keys) {
        Set<String> res = null;
        try {
            res = jedisCluster.sdiff(keys);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long sdiffstore(String dstkey, String... keys) {
        Long res = null;
        try {
            res = jedisCluster.sdiffstore(dstkey, keys);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Set<String> sinter(String... keys) {
        Set<String> res = null;
        try {
            res = jedisCluster.sinter(keys);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long sinterstore(String dstkey, String... keys) {
        Long res = null;
        try {
            res = jedisCluster.sinterstore(dstkey, keys);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Set<String> sunion(String... keys) {
        Set<String> res = null;
        try {
            res = jedisCluster.sunion(keys);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long sunionstore(String dstkey, String... keys) {
        Long res = null;
        try {
            res = jedisCluster.sunionstore(dstkey, keys);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long smove(String srckey, String dstkey, String member) {
        Long res = null;
        try {
            res = jedisCluster.smove(srckey, dstkey, member);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long scard(String key) {
        Long res = null;
        try {
            res = jedisCluster.scard(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Boolean sismember(String key, String member) {
        Boolean res = null;
        try {
            res = jedisCluster.sismember(key, member);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public String srandmember(String key) {
        String res = null;
        try {
            res = jedisCluster.srandmember(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Set<String> smembers(String key) {
        Set<String> res = null;
        try {
            res = jedisCluster.smembers(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }

    @Override
    public Long zadd(String key, double score, String member) {
        Long res = null;
        try {
            res = jedisCluster.zadd(key, score, member);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }

    @Override
    public Long zrem(String key, String... members) {
        Long res = null;
        try {
            res = jedisCluster.zrem(key, members);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Double zincrby(String key, double score, String member) {
        Double res = null;
        try {
            res = jedisCluster.zincrby(key, score, member);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long zrank(String key, String member) {
        Long res = null;
        try {
            res = jedisCluster.zrank(key, member);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long zrevrank(String key, String member) {
        Long res = null;
        try {
            res = jedisCluster.zrevrank(key, member);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        Set<String> res = null;
        try {
            res = jedisCluster.zrevrange(key, start, end);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Set<String> zrangebyscore(String key, String max, String min) {
        Set<String> res = null;
        try {
            res = jedisCluster.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Set<String> zrangeByScore(String key, double max, double min) {
        Set<String> res = null;
        try {
            res = jedisCluster.zrevrangeByScore(key, max, min);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long zcount(String key, String min, String max) {
        Long res = null;
        try {
            res = jedisCluster.zcount(key, min, max);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long zcard(String key) {
        Long res = null;
        try {
            res = jedisCluster.zcard(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Double zscore(String key, String member) {
        Double res = null;
        try {
            res = jedisCluster.zscore(key, member);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        Long res = null;
        try {
            res = jedisCluster.zremrangeByRank(key, start, end);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }


    @Override
    public Long zremrangeByScore(String key, double start, double end) {
        Long res = null;
        try {
            res = jedisCluster.zremrangeByScore(key, start, end);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }



    @Override
    public String type(String key) {
        String res = null;
        try {
            res = jedisCluster.type(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        }
        return res;
    }



    @Override
    public Date getExpireDate(String key) {
        Date res = new Date();
        try {
            res = new DateTime().plusSeconds(jedisCluster.ttl(key).intValue()).toDate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return res;
    }
}
