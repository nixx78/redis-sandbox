package lv.nixx.samples.redis.stream;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/messages/stream")
public class RedisStreamController {

    private final RedisStreamService streamService;

    public RedisStreamController(RedisStreamService streamService) {
        this.streamService = streamService;
    }

    @GetMapping
    public List<MapRecord<String, Object, Object>> getMessages() {
        return streamService.getAllMessages();
    }

    @PostMapping
    public String addMessage(@RequestBody Map<String, String> payload) {
        return streamService.addMessage(payload).getValue();
    }

    @DeleteMapping
    public void deleteAllMessages() {
        streamService.deleteAllMessages();
    }
}
