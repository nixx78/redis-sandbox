package lv.nixx.samples.redis.search;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.search.SearchProtocol;

import java.util.Collection;
import java.util.HashSet;

@Service
public class SuggestionService {

    public static final String PHOTO = "photo";
    private final JedisPool jedisPool;

    public SuggestionService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void reindex() {

        try (Jedis jedis = jedisPool.getResource()) {

            Collection<String> elemsInSet = jedis.smembers(PHOTO);

            Collection<String> places = new HashSet<>();

            for (String id : elemsInSet) {
                String p = jedis.hget("photo:" + id, "place");
                places.add(p);
            }

            for (String p : places) {
                jedis.sendCommand(SearchProtocol.SearchCommand.SUGADD, "photo:suggest:place", p, "1");
            }
        }
    }

    public Collection<String> getSuggestion(String word) {
        try (Jedis jedis = jedisPool.getResource()) {
            Collection<byte[]> o = (Collection<byte[]>) jedis.sendCommand(SearchProtocol.SearchCommand.SUGGET, "photo:suggest:place", word, "MAX", "5");

            return o.stream().map(String::new).toList();
        }
    }


}
