package lv.nixx.samples.redis.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Picture {
    String id;
    LocalDateTime dateTime;
    String location;
}

