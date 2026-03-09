package lv.nixx.cache.redis.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lv.nixx.cache.redis.object.Person;
import lv.nixx.cache.redis.object.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheWithLoaderConfig {

    private static final Logger log = LoggerFactory.getLogger(CacheWithLoaderConfig.class);

    @Bean
    public LoadingCache<String, Person> personCache(PersonService personService) {

        return Caffeine.newBuilder()
                .maximumSize(10_000)
                .recordStats()

                .refreshAfterWrite(2, TimeUnit.SECONDS)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .executor(Executors.newFixedThreadPool(4))

                // CacheLoader
                .build(new CacheLoader<>() {

                    @Override
                    public Person load(String key) {
                        log.info("Loading person from service: {}", key);
                        return personService.loadByKey(key);
                    }

                    @Override
                    public Person reload(String key, Person oldValue) {

                        Person person = personService.loadByKey(key);

                        log.info("Reloading person from service: {}\n\t old value {}\n\t new value {}", oldValue, person, key);
                        return person;
                    }
                });
    }


}
