package lv.nixx.samples.redis.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisStreamListenerUsingThread {

    private static final Logger log = LoggerFactory.getLogger(RedisStreamListenerUsingThread.class);

    private static final String STREAM_KEY = "mystream";
    private static final String GROUP = "mygroup";
    private static final String CONSUMER = "consumer-1";

    private final StringRedisTemplate redisTemplate;

    public RedisStreamListenerUsingThread(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


// Just sample how is possible to create listener using Thread
//    @PostConstruct
//    public void init() {
//        StreamOperations<String, Object, Object> opsForStream = redisTemplate.opsForStream();
//        try {
//            opsForStream.createGroup(STREAM_KEY, ReadOffset.from("0-0"), GROUP);
//        } catch (Exception ignored) {
//        }
//
//        new Thread(() -> {
//            while (true) {
//                try {
//                    List<MapRecord<String, Object, Object>> messages =
//                            opsForStream.read(
//                                    Consumer.from(GROUP, CONSUMER),
//                                    StreamReadOptions.empty().block(Duration.ofSeconds(2)),
//                                    StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed())
//                            );
//
//                    if (messages != null) {
//                        for (MapRecord<String, Object, Object> message : messages) {
//                            log.info("Message received (Thread listener): {} ", message.getValue());
//                            opsForStream.acknowledge(STREAM_KEY, GROUP, message.getId());
//                        }
//                    }
//                } catch (RedisSystemException ex) {
//                    log.warn("Redis exception: {}", ex.getMessage());
//                }
//            }
//        }).start();
//    }

}
