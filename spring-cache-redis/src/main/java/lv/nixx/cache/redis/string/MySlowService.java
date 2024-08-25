package lv.nixx.cache.redis.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MySlowService {

    private static final Logger log = LoggerFactory.getLogger(MySlowService.class);

    private static final String CACHE_NAME = "sandbox-cache";

    private final Map<String, String> storage = new HashMap<>();

    @CachePut(value = CACHE_NAME, key = "#id")
    public String add(String id, String value) {
        storage.put(id, value);

        return value;
    }

    @CacheEvict(value = CACHE_NAME, key = "#id")
    public String updateEntity(String id, String newValue) {
        storage.put(id, newValue);

        return newValue;
    }

    @CacheEvict(value = CACHE_NAME, key = "#id")
    public String delete(String id) {
        return storage.remove(id);
    }

    @Cacheable(value = CACHE_NAME, key = "#id")
    public String getValueById(String id) {
        try {
            TimeUnit.SECONDS.sleep(5);
            String v = storage.get(id);
            log.info("Slow service response, id [{}] value [{}]", id, v);
            return v;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void clearCache() {
        log.info("Clear cache");
    }

}