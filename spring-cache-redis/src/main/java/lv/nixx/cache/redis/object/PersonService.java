package lv.nixx.cache.redis.object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    public static final String CACHE_NAME = "person-cache";

    private final Map<String, Person> storage = new HashMap<>(Map.of(
            "id1", new Person("id1", "Name1"),
            "id2", new Person("id2", "Name2"),
            "id3", new Person("id3", "Name3")
    ));

    @CachePut(value = CACHE_NAME, key = "#result.id")
    public Person add(Person newPerson) {
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
            Person v = storage.get(id);
            if (v == null) {
                throw new IllegalArgumentException("Person with id [%s] not found".formatted(id));
            }

            TimeUnit.SECONDS.sleep(2);

            log.info("Slow Person service, response, id [{}] value [{}]", id, v);
            return v;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void clearCache() {
        log.info("Clear cache");
    }

    public Collection<Person> getAll() {
        return storage.values();
    }
}