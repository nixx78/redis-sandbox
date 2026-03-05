package lv.nixx.cache.redis.object;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

import static lv.nixx.cache.redis.object.PersonService.CACHE_NAME;

@Service
@RequiredArgsConstructor
public class PersonCacheControlService {

    private static final Logger log = LoggerFactory.getLogger(PersonCacheControlService.class);

    private final CacheManager cacheManager;

    public void clear() {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        if (cache != null) {
            cache.clear();
            log.info("All values removed from cache: [{}]", CACHE_NAME);

        }
    }

    public void loadData() {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        if (cache != null) {

            log.info("Add values directly to cache: [{}]", CACHE_NAME);

            Stream.of(new Person("id1.c", "Name1.c"),
                    new Person("id2.c", "Name2.c"),
                    new Person("id3.c", "Name3.c")
            ).forEach(t -> {
                cache.putIfAbsent(t.getId(), t);

                log.info("\t\t {}", t);
            });


        }
    }
}
