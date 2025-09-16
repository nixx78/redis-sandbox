package lv.nixx.samples.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

@Configuration
public class EmbeddedRedisAutoConfig {

    @Bean(destroyMethod = "stop")
    public RedisServer embeddedRedisServer() throws Exception {
        RedisServer server = RedisServer.newRedisServer()
                .port(6379)
                .build();

        server.start();

        return server;
    }

}

