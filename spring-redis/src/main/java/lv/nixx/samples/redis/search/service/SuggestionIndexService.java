package lv.nixx.samples.redis.search.service;

import lv.nixx.samples.redis.search.model.IndexField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.search.SearchProtocol;

import java.util.Collection;
import java.util.HashSet;

@Service
public class SuggestionIndexService {

    private final static Logger log = LoggerFactory.getLogger(SuggestionIndexService.class);

    public static final String PHOTO = "photo";
    public static final String SUGGEST = ":suggest:";

    private final JedisPool jedisPool;

    public SuggestionIndexService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public long createIndex(IndexField indexField) {
        try (Jedis jedis = jedisPool.getResource()) {

            String field = indexField.name();

            Collection<String> elemsInSet = jedis.smembers(PHOTO);

            Collection<String> valuesForIndex = new HashSet<>();

            for (String id : elemsInSet) {
                String p = jedis.hget(PHOTO + ":" + id, field);
                valuesForIndex.add(p);
            }

            for (String p : valuesForIndex) {
                jedis.sendCommand(SearchProtocol.SearchCommand.SUGADD, PHOTO + SUGGEST + field, p, "1");
            }

            return createIndex(indexField, valuesForIndex);
        }
    }

    public long createIndex(IndexField indexField, Collection<String> valuesForIndex) {

        try (Jedis jedis = jedisPool.getResource()) {
            String field = indexField.name();

            for (String p : valuesForIndex) {
                jedis.sendCommand(SearchProtocol.SearchCommand.SUGADD, PHOTO + SUGGEST + field, p, "1");
            }

            Long size = (Long) jedis.sendCommand(SearchProtocol.SearchCommand.SUGLEN, PHOTO + SUGGEST + field);
            log.info("Suggestion index added, field: [{}] count: [{}]", field, size);

            return size;
        }
    }

    public void deleteIndex(IndexField indexField) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = PHOTO + SUGGEST + indexField.name();
            jedis.del(key);

            log.info("Suggestion index [{}] deleted", key);
        }
    }

    public Collection<String> getSuggestion(IndexField field, String word) {
        try (Jedis jedis = jedisPool.getResource()) {
            Collection<byte[]> o = (Collection<byte[]>) jedis.sendCommand(SearchProtocol.SearchCommand.SUGGET, PHOTO + SUGGEST + field.name(), word, "MAX", "5");

            return o.stream().map(String::new).toList();
        }
    }

}
