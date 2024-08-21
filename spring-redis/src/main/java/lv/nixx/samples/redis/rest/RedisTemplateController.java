package lv.nixx.samples.redis.rest;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class RedisTemplateController {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisTemplateController(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/redisTemplate")
    public void getPage() {

        HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();

        stringObjectObjectHashOperations.put("person:777", "name", "name.777");
        stringObjectObjectHashOperations.put("person:777", "personId", "person:777");

        Object o1 = stringObjectObjectHashOperations.get("person:777", "f1");
    }

}
