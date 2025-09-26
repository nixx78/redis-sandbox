package lv.nixx.samples.redis.search.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@RedisHash("photo")
@Builder
public record Photo(
        @Id String id,
        String filename,
        Genre genre,
        Collection<String> keywords,
        String place,
        Double rating,
        Long captureDateTime
) implements Serializable {

    public String getCaptureDateTimeISO() {

        if (captureDateTime == null) {
            return null;
        }

        LocalDateTime dateTime = Instant.ofEpochMilli(captureDateTime)
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();

        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
