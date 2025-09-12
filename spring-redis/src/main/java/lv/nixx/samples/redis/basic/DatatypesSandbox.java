package lv.nixx.samples.redis.basic;

import redis.clients.jedis.Jedis;

import java.util.Collection;

public class DatatypesSandbox {

    private static final String sKey = "s_key";
    private static final String listKey = "mylist";
    private static final String setKey = "set_key";
    private static final String zsetKey = "zset_key";

    public static void main(String[] args) {

        try (Jedis jedis = new Jedis("localhost", 6379)) {

            cleanup(jedis);

            System.out.println("Ping from Redis: " + jedis.ping());

            // Store value as Key value

            jedis.set(sKey, "s_key.value");
            System.out.println("Value for key: " + jedis.get(sKey));

            // Store values to list
            jedis.lpush(listKey, "a", "b"); // add to left
            jedis.rpush(listKey, "c");      // add to right

            System.out.println("Elements in List: " + jedis.lrange(listKey, 0, -1));
            System.out.println("Elements in List (index: 1): " + jedis.lindex(listKey, 1));

            // Store values in set
            jedis.sadd(setKey, "Alpha", "Beta", "Gama");

            Collection<String> elemsInSet = jedis.smembers(setKey);
            System.out.println("Elements in Set: " + elemsInSet);

            // Store value in sorted set
            jedis.zadd(zsetKey, 300, "Value300");
            jedis.zadd(zsetKey, 100.0, "Value1");
            jedis.zadd(zsetKey, 101.9, "Value1");
            jedis.zadd(zsetKey, 200, "Value200");

            System.out.println("Elements in ZSet: " + jedis.zrange(zsetKey, 0, -1));
            System.out.println("ZSet values in range with score: " + jedis.zrangeByScoreWithScores(zsetKey, 100, 200));
        }

    }

    private static void cleanup(Jedis jedis) {
        jedis.del(setKey, listKey, setKey, zsetKey);
    }

}
