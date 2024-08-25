package lv.nixx.cache.redis.string;

import lombok.Data;

@Data
public class Request {
    private String id;
    private String value;
}
