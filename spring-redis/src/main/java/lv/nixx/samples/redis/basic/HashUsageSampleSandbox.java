package lv.nixx.samples.redis.basic;

import redis.clients.jedis.Jedis;

import java.util.Map;

public class HashUsageSampleSandbox {

    private static final String USER_1001_KEY = "user:1001";




    public static void main(String[] args) {

        try (Jedis jedis = new Jedis("localhost", 6379)) {

            System.out.println("Ping from Redis: " + jedis.ping());

            /*
            In Redis, there is no concept of separate maps (like in Hazelcast) — all data is stored in a single keyspace.
            To logically separate data, you can use techniques such as adding a prefix to the key names.
            Alternatively, Redis also provides logical databases (selected with SELECT n), but in practice key prefixes are the more common and recommended approach,
             especially when working with libraries and clusters.
             */

            jedis.hset(USER_1001_KEY, Map.of(
                    "id", "1001",
                    "name", "John Doe",
                    "timestamp", "" + System.currentTimeMillis()
            ));

            System.out.println("User from Redis (after add): " + jedis.hgetAll(USER_1001_KEY));

            //Add more fields to existing structure
            jedis.hset(USER_1001_KEY, Map.of(
                    "newField", "NewField.Value",
                    "fieldToDelete", "FieldToDelete.Value"
            ));

            Map<String, String> stringStringMap = jedis.hgetAll(USER_1001_KEY);
            System.out.println("User from Redis (after new fields add): " + stringStringMap);

            //Update existing field
            jedis.hset(USER_1001_KEY, Map.of(
                    "newField", "NewField.Updated"
            ));
            System.out.println("User from Redis (after existing field update): " + jedis.hgetAll(USER_1001_KEY));

            jedis.hdel(USER_1001_KEY, "fieldToDelete");
            System.out.println("User from Redis (after field delete): " + jedis.hgetAll(USER_1001_KEY));

            System.out.println("DB size:" + jedis.dbSize());

//            // Delete all data related to key
//            jedis.del(USER_1001_KEY);
//            jedis.flushDB();
//
//            System.out.println("DB size:" + jedis.dbSize());
//            System.out.println("User from Redis after delete: " + jedis.hgetAll(USER_1001_KEY));
        }
    }

}
