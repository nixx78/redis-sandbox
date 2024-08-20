package lv.nixx.sandbox.redis;

import redis.clients.jedis.Jedis;

import java.util.Map;

public class DataLoadSandbox {

    public static void main(String[] args) {

        try (Jedis jedis = new Jedis("localhost", 6379)) {

            System.out.println("Ping from Redis: " + jedis.ping());

            for (int i = 0; i < 1000; i++) {
                jedis.hset("user_xxx:" + i, Map.of(
                        "id", "u" + i,
                        "name", "John Doe",
                        "timestamp", "" + System.currentTimeMillis()
                ));

            }
        }

    }

}
