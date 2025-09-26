package lv.nixx.samples.redis.search.model;

import java.time.LocalDateTime;

public record PhotoSearchRequest (
        Genre genre,
        String keyword,
        String place,
        Double minRating,
        Double maxRating,
        LocalDateTime captureDateTimeFrom,
        LocalDateTime captureDateTimeTo
) {
}
