package lv.nixx.samples.redis.stream;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;

@Configuration
public class RedisStreamListenerConfig {

    private static final Logger log = LoggerFactory.getLogger(RedisStreamService.class);

    private static final String STREAM_KEY = "mystream";
    private static final String GROUP_A = "group_a";
    private static final String GROUP_B = "group_b";

    private final StringRedisTemplate redisTemplate;

    public RedisStreamListenerConfig(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void setup() {

        StreamOperations<String, Object, Object> opsForStream = redisTemplate.opsForStream();
        try {
            opsForStream.createGroup(STREAM_KEY, ReadOffset.from("0-0"), GROUP_A);
        } catch (Exception ignored) {
        }

        try {
            opsForStream.createGroup(STREAM_KEY, ReadOffset.from("0-0"), GROUP_B);
        } catch (Exception ignored) {
        }

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
                .pollTimeout(Duration.ofSeconds(2))
                .build();

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container = StreamMessageListenerContainer.create(redisTemplate.getConnectionFactory(), options);

        container.receive(
                Consumer.from(GROUP_A, "consumerA"),
                StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed()),
                message -> {
                    log.info("Listener A receive: {}", message.getValue());
                    opsForStream.acknowledge(STREAM_KEY, GROUP_A, message.getId());
                });

        container.receive(
                Consumer.from(GROUP_B, "consumerB"),
                StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed()),
                message -> {
                    log.info("Listener B receive: {}", message.getValue());
                    opsForStream.acknowledge(STREAM_KEY, GROUP_B, message.getId());
                });

        container.start();

        log.info("Listeners for stream [{}] created", STREAM_KEY);

    }
}
