package lv.nixx.cache.redis.string;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static lv.nixx.cache.redis.string.ServiceWithCoffeineCache.CACHE_IN_CUSTOM_MANAGER;

@RestController
@RequestMapping("/keyvalue")
public class ControllerForCoffeineService {

    private static final Logger log = LoggerFactory.getLogger(ControllerForCoffeineService.class);

    private final ServiceWithCoffeineCache serviceWithCoffeineCache;
    private final CaffeineCacheManager coffeineCacheManager;

    public ControllerForCoffeineService(ServiceWithCoffeineCache serviceWithCoffeineCache,
                                        @Qualifier("coffeineCacheManager") CaffeineCacheManager coffeineCacheManager
    ) {
        this.serviceWithCoffeineCache = serviceWithCoffeineCache;
        this.coffeineCacheManager = coffeineCacheManager;
    }

    @GetMapping("/serviceWithCaffeine/{id}")
    public String callServiceWithCustomManager(@PathVariable String id) {
        log.info("Method: callServiceWithCustomManager() call, id [{}]", id);
        return serviceWithCoffeineCache.getValueById(id);
    }

    @GetMapping("/caffeineCacheManagerExplorer")
    public void explore() {
        CaffeineCache cache = (CaffeineCache) coffeineCacheManager.getCache(CACHE_IN_CUSTOM_MANAGER);

        Cache<Object, Object> nativeCache = cache.getNativeCache();
        CacheStats stats = nativeCache.stats();

        System.out.println(stats);
    }

    @GetMapping("/caffeineLoader")
    public void load() {
        CaffeineCache cache = (CaffeineCache) coffeineCacheManager.getCache(CACHE_IN_CUSTOM_MANAGER);

        cache.invalidate();

        Map.of(
                "id1", "value1",
                "id2", "value1",
                "id3", "value1"
        ).forEach(cache::put);

    }


}
