package lv.nixx.cache.redis.object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    private static final String CACHE_NAME = "person-cache";

    private final Map<String, Person> storage = new HashMap<>();

    @CachePut(value = CACHE_NAME, key = "#newPerson.id")
    public Person add(Person newPerson) {
        //TODO how to pass null key and use id from returned value?

        String id = UUID.randomUUID().toString();
        storage.put(id, newPerson);

        return new Person(id, newPerson.getName());
    }

    @CacheEvict(value = CACHE_NAME, key = "#updatedPerson.id")
    public Person updateEntity(Person updatedPerson) {
        storage.put(updatedPerson.getId(), updatedPerson);

        return updatedPerson;
    }

    @CacheEvict(value = CACHE_NAME, key = "#id")
    public Person delete(String id) {
        return storage.remove(id);
    }

    @Cacheable(value = CACHE_NAME, key = "#id")
    public Person getValueById(String id) {
        try {
            TimeUnit.SECONDS.sleep(5);
            Person v = storage.get(id);
            log.info("Slow service person service, response, id [{}] value [{}]", id, v);
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