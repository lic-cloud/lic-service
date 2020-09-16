package cn.bestsort.cache;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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


    @Bean
    public JedisPool redisPoolFactory() {
        JedisPoolConfig config = new JedisPoolConfig();
        //TODO
        JedisPool pool = new JedisPool(config, "localhost",
                                       6379,
                                       Protocol.DEFAULT_TIMEOUT,
                                       null,
                                       0);

        log.info("redis pool build success");
        log.info("redis start success, redis host: {}", AnsiOutput.toString(
            AnsiColor.BLUE,
            "localhost",
            ":",
            6379));
        return pool;
    }
}

