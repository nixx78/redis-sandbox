package lv.nixx.cache.redis.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ServiceWithCoffeineCache {

    private static final Logger log = LoggerFactory.getLogger(ServiceWithCoffeineCache.class);

    public static final String CACHE_IN_CUSTOM_MANAGER = "cache-in-custom-manager";
    public static final String COFFEINE_CACHE_MANAGER = "coffeineCacheManager";

    @Cacheable(value = CACHE_IN_CUSTOM_MANAGER, key = "#id", cacheManager = COFFEINE_CACHE_MANAGER)
    public String getValueById(String id) {
        try {
            TimeUnit.SECONDS.sleep(2);
            log.info("Service call, return real value");
            return "RealValue:" + id + ":" + System.currentTimeMillis();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @CacheEvict(value = CACHE_IN_CUSTOM_MANAGER, allEntries = true, cacheManager = COFFEINE_CACHE_MANAGER)
    public void clearCache() {
        log.info("Clear cache");
    }

}