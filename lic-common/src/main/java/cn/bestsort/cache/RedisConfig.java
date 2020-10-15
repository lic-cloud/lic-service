package cn.bestsort.cache;

import javax.annotation.Resource;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-16 08:25
 */

@Slf4j
@Getter
@Setter
@Configuration
@EnableAutoConfiguration
public class RedisConfig {

    @Resource
    RedisProperties properties;

    @Bean
    public JedisPool redisPoolFactory() {
        JedisPoolConfig config = new JedisPoolConfig();
        if (StringUtils.isEmpty(properties.getPassword())) {
            properties.setPassword(null);
        }
        JedisPool pool = new JedisPool(
            config,
            properties.getHost(),
            properties.getPort(),
            Protocol.DEFAULT_TIMEOUT,
            properties.getPassword());
        log.info("redis pool build success");
        log.info("redis start success, redis host: {}", AnsiOutput.toString(
            AnsiColor.BLUE,
            properties.getHost(),
            ":",
            properties.getPort()));
        return pool;
    }
}

