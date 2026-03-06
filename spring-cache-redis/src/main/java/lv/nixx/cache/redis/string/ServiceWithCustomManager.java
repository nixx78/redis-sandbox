package lv.nixx.cache.redis.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ServiceWithCustomManager {

    private static final Logger log = LoggerFactory.getLogger(ServiceWithCustomManager.class);

    private static final String CACHE_IN_CUSTOM_MANAGER = "cache-in-custom-manager";

    @Cacheable(value = CACHE_IN_CUSTOM_MANAGER, key = "#id", cacheManager = "internalCacheManager")
    public String getValueById(String id) {
        try {
            TimeUnit.SECONDS.sleep(2);
            log.info("Service call, return real value");
            return "RealValue:" + id + ":" + System.currentTimeMillis();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @CacheEvict(value = CACHE_IN_CUSTOM_MANAGER, allEntries = true, cacheManager = "internalCacheManager")
    public void clearCache() {
        log.info("Clear cache");
    }

}