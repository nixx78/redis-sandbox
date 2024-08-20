package lv.nixx.samples.redis.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("person")
@Data
public class Person implements Serializable {

    @Id
    private Long personId;

    private String name;
}
