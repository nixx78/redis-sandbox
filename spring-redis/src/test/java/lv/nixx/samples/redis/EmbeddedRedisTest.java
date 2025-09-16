package lv.nixx.samples.redis;


import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmbeddedRedisTest {

    private RedisServer redisServer;
    private Jedis jedis;

    @BeforeAll
    void startRedis() throws Exception {
        redisServer = RedisServer.newRedisServer()
                .port(6379)
                .build();
        redisServer.start();

        jedis = new Jedis("localhost", 6379);
    }

    @AfterAll
    void stopRedis() throws IOException {
        jedis.close();
        redisServer.stop();
    }

    @Test
    void testSetAndGet() {
        jedis.set("mykey", "hello");
        Assertions.assertEquals("hello", jedis.get("mykey"));
    }
}
