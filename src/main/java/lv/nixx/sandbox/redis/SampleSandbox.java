package lv.nixx.sandbox.redis;

import redis.clients.jedis.Jedis;

import java.util.Map;

public class SampleSandbox {

    private static final String USER_1001_KEY = "user:1001";

    public static void main(String[] args) {

        try (Jedis jedis = new Jedis("localhost", 6379)) {

            System.out.println("Ping from Redis: " + jedis.ping());

            /* В Redis нет разделения по мапам (как в Hazelcast) все хранится в общей структуре,
            отделять их можно например по префиксу ключа
            * */
            jedis.hset(USER_1001_KEY, Map.of(
                    "id", "1001",
                    "name", "John Doe",
                    "timestamp", "" + System.currentTimeMillis()
            ));

            System.out.println("User from Redis: " + jedis.hgetAll(USER_1001_KEY));

            //Добавим еще одно поле, оно будет добавленно к уже существующей структуре
            jedis.hset(USER_1001_KEY, Map.of(
                    "newField", "NewField.Value"
            ));

            System.out.println("User from Redis: " + jedis.hgetAll(USER_1001_KEY));

            // Удалим все данных связанные с ключем
            jedis.del(USER_1001_KEY);
        }

    }

}
