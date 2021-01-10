package com.alexzheng.onlineshop.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author Alex Zheng
 * @Date 2020/6/10 14:50
 * @Annotation
 */
public class JedisPoolWriper {

    /**
     * redis连接池对象
     */
    private JedisPool jedisPool;

    /**
     * 连接池对象初始化
     */
    public JedisPoolWriper(final JedisPoolConfig poolConfig,final String host,final int port){
        try{
            jedisPool = new JedisPool(poolConfig,host,port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
