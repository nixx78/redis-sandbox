package lv.nixx.cache.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO Сделать пример, для сохранения в кеше коллекции объектов (запись напрямую в кеш, RedisTemplate/CacheManager)

@SpringBootApplication
public class SpringCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCacheApplication.class, args);
    }

}
