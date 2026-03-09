package lv.nixx.cache.redis.object;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CacheWithLoaderController {

    private final LoadingCache<String, Person> personCache;

    @GetMapping("/cacheLoader/person/")
    public Person getById(@RequestParam String id) {
        return personCache.get(id);
    }

    @GetMapping("/cacheLoader/statistic")
    public Map<String, Object> getStats() {
        CacheStats stats = personCache.stats();

        return Map.of(
                "hitCount", stats.hitCount(),
                "missCount", stats.missCount(),
                "loadSuccessCount", stats.loadSuccessCount(),
                "evictionCount", stats.evictionCount(),
                "hitRate", stats.hitRate()
        );
    }

}
