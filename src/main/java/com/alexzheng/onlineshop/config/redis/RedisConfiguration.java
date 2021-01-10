package com.alexzheng.onlineshop.config.redis;

import com.alexzheng.onlineshop.cache.JedisPoolWriper;
import com.alexzheng.onlineshop.cache.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author Alex Zheng
 * @Date 2020/6/14 18:24
 * @Annotation
 */
@Configuration
public class RedisConfiguration {

//    @Value("${redis.hostname}")
//    private String hostname;
//    @Value("${redis.port}")
//    private int port;
//    @Value("${redis.pool.maxActive}")
//    private int maxTotal;
//    @Value("${redis.pool.maxIdle}")
//    private int maxIdle;
//    @Value("${redis.pool.maxWait}")
//    private long maxWaitMillis;
//    @Value("${redis.pool.testOnBorrow}")
//    private boolean testOnBorrow;
//
//
//    @Autowired
//    private JedisPoolConfig jedisPoolConfig;
//    @Autowired
//    private JedisPoolWriper jedisWritePool;
//    @Autowired
//    private JedisUtil jedisUtil;
//
//    /**
//     * 创建redis连接池的设置
//     *
//     * @return
//     */
//    @Bean(name = "jedisPoolConfig")
//    public JedisPoolConfig createJedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        // 控制一个pool可分配多少个jedis实例
//        jedisPoolConfig.setMaxTotal(maxTotal);
//        // 连接池中最多可空闲maxIdle个连接
//        // 在这里表示即使没有数据库连接时依然可以保持20空闲的连接，而不被清除，随时处于待命状态。
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        // 最大等待时间:当没有可用连接时,
//        // 连接池等待连接被归还的最大时间(以毫秒计数),超过时间则抛出异常
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        // 在获取连接的时候检查有效性
//        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
//        //如果设置为true就需要将redis和你的程序放到同一台机器上或者同一局域网上面或者关闭该模式。
//        jedisPoolConfig.setTestOnCreate(false);
//        return jedisPoolConfig;
//    }
//
//    /**
//     * 创建Redis连接池，并做相关配置
//     *
//     * @return
//     */
//    @Bean(name = "jedisWritePool")
//    public JedisPoolWriper createJedisPoolWriper() {
//        //构造器接收三个参数
//        JedisPoolWriper jedisPoolWriper = new JedisPoolWriper(jedisPoolConfig, hostname, port);
//        return jedisPoolWriper;
//    }
//
//    /**
//     * 创建Redis工具类，封装好Redis的连接以进行相关的操作
//     *
//     * @return
//     */
//    @Bean(name = "jedisUtil")
//    public JedisUtil createJedisUtil() {
//        JedisUtil jedisUtil = new JedisUtil();
//        //默认为singleton 不用设置
//        jedisUtil.setJedisPool(jedisWritePool);
//        return jedisUtil;
//    }
//
//    /**
//     * Redis的key操作
//     *
//     * @return
//     */
//    @Bean(name = "jedisKeys")
//    public JedisUtil.Keys createJedisKeys() {
//        JedisUtil.Keys jedisKeys = jedisUtil.new Keys();
//        return jedisKeys;
//    }
//
//    /**
//     * Redis的Strings操作
//     *
//     * @return
//     */
//    @Bean(name = "jedisStrings")
//    public JedisUtil.Strings createJedisStrings() {
//        JedisUtil.Strings jedisStrings = jedisUtil.new Strings();
//        return jedisStrings;
//    }




}