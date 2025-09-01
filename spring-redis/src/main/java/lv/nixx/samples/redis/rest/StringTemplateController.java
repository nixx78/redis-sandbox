package lv.nixx.samples.redis.rest;

import lv.nixx.samples.redis.Picture;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class StringTemplateController {

    private static final String ID_PREFIX = "spicture.";

    private final HashOperations<String, String, Object> hashOps;

    public StringTemplateController(StringRedisTemplate stringRedisTemplate) {
        this.hashOps = stringRedisTemplate.opsForHash();
    }

    @PostMapping("/string/picture")
    public Picture addPicture(@RequestBody Picture picture) {
        String key = ID_PREFIX + picture.getId();

        hashOps.putAll(key, Map.of(
                "id", key,
                "dateTime", picture.getDateTime().toString(),
                "location", picture.getLocation()
        ));

        return picture;
    }

    @GetMapping("/string/picture")
    public Picture getPicture(@RequestParam String id) {
        String key = ID_PREFIX + id;

        Map<String, Object> allFields = hashOps.entries(key);

        return new Picture()
                .setId((String) allFields.get("id"))
                .setDateTime(LocalDateTime.parse((String) allFields.get("dateTime")))
                .setLocation((String) allFields.get("location"));

    }


}
