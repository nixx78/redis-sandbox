package lv.nixx.samples.redis.rest;

import lv.nixx.samples.redis.model.Picture;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class JsonOperationsController {

    public static final String ID_PREFIX = "jpicture.";
    private final HashOperations<String, String, Object> hashOps;

    public JsonOperationsController(RedisTemplate<String, Object> jsonRedisTemplate) {
        this.hashOps = jsonRedisTemplate.opsForHash();
    }

    @PostMapping("/json/picture")
    public Picture addPicture(@RequestBody Picture picture) {
        String key = ID_PREFIX + picture.getId();
        hashOps.put(key, "data", picture);

        return (Picture) hashOps.get(key, "data");
    }

    @GetMapping("/json/picture")
    public Picture getPicture(@RequestParam String id) {
        String key = ID_PREFIX + id;
        return (Picture) hashOps.get(key, "data");
    }


}
