package lv.nixx.samples.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//TODO Сделать пример, для сохранения в кеше объекта вместо строки
//TODO Сделать пример, для сохранения в кеше коллекции объектов (запись напрямую в кеш, RedisTemplate/CacheManager)

@SpringBootApplication
public class RedisSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisSpringApplication.class, args);
    }

}
