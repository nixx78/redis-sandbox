package lv.nixx.samples.redis.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RedisStreamService {

    private final static Logger log = LoggerFactory.getLogger(RedisStreamService.class);

    private static final String STREAM_KEY = "mystream";

    private final StreamOperations<String, Object, Object> opsForStream;

    public RedisStreamService(StringRedisTemplate redisTemplate) {
        this.opsForStream = redisTemplate.opsForStream();
    }

    public List<MapRecord<String, Object, Object>> getAllMessages() {
        return opsForStream.range(STREAM_KEY, Range.unbounded());
    }

    public RecordId addMessage(Map<String, String> fields) {
        ObjectRecord<String, Map<String, String>> record = ObjectRecord.create(STREAM_KEY, fields);
        return opsForStream.add(record);
    }

    public void deleteAllMessages() {
        Long trim = opsForStream.trim(STREAM_KEY, 0);
        log.info("From stream [{}] removed [{}] messages", STREAM_KEY, trim);
    }

}
