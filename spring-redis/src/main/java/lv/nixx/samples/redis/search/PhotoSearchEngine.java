package lv.nixx.samples.redis.search;

import lv.nixx.samples.redis.search.model.Genre;
import lv.nixx.samples.redis.search.model.Photo;
import lv.nixx.samples.redis.search.model.PhotoSearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.SearchProtocol;

import java.time.ZoneOffset;
import java.util.*;

@Service
public class PhotoSearchEngine {

    private final static Logger log = LoggerFactory.getLogger(PhotoSearchEngine.class);

    private final JedisPooled jedis;

    public PhotoSearchEngine(@Value("${spring.redis.host}") String host,
                             @Value("${spring.redis.port}") Integer port
    ) {

        this.jedis = new JedisPooled(host, port);

        log.info("Jedis client for search created: {}", this.jedis.info("Server"));
    }

    public Collection<Photo> search(PhotoSearchRequest request) {
        StringBuilder query = new StringBuilder();

        if (request.genre() != null) {
            query.append(" @genre:{").append(request.genre()).append("}");
        }

        if (request.keyword() != null) {
            query.append(" @keywords:{").append(request.keyword()).append("}");
        }

        if (request.place() != null) {
            query.append(" @place:").append(request.place());
        }

        if (request.minRating() != null || request.maxRating() != null) {
            double min = (request.minRating() != null) ? request.minRating() : Double.NEGATIVE_INFINITY;
            double max = (request.maxRating() != null) ? request.maxRating() : Double.POSITIVE_INFINITY;
            query.append(" @rating:[").append(min).append(" ").append(max).append("]");
        }

        if (request.captureDateTimeFrom() != null || request.captureDateTimeTo() != null) {
            long min = (request.captureDateTimeFrom() != null)
                    ? request.captureDateTimeFrom().toInstant(ZoneOffset.UTC).toEpochMilli()
                    : Long.MIN_VALUE;
            long max = (request.captureDateTimeTo() != null)
                    ? request.captureDateTimeTo().toInstant(ZoneOffset.UTC).toEpochMilli()
                    : Long.MAX_VALUE;

            query.append(" @captureDateTime:[").append(min).append(" ").append(max).append("]");
        }

        String queryAsString = query.toString();
        log.info("Query from Redis using string [{}]", queryAsString);
        Object result = jedis.sendCommand(SearchProtocol.SearchCommand.SEARCH, "photo-idx", queryAsString);

        List<Object> rawResult = (List<Object>) result;
        List<Photo> photos = new ArrayList<>();

        for (int i = 1; i < rawResult.size(); i += 2) {
            byte[] o = (byte[]) rawResult.get(i);
            String id = new String(o);
            List<byte[]> fields = (List<byte[]>) rawResult.get(i + 1);

            List<String> keywords = new ArrayList<>();
            Map<String, String> fieldsMap = new HashMap<>();
            for (int j = 0; j < fields.size(); j += 2) {
                String key = new String(fields.get(j));
                String value = new String(fields.get(j + 1));
                if (key.contains("keywords")) {
                    keywords.add(value);
                } else {
                    fieldsMap.put(key, value);
                }
            }

            Photo photo = Photo.builder()
                    .id(id)
                    .filename(fieldsMap.get("filename"))
                    .genre(fieldsMap.get("genre") != null ? Genre.valueOf(fieldsMap.get("genre")) : null)
                    .keywords(keywords)
                    .place(fieldsMap.get("place"))
                    .rating(fieldsMap.get("rating") != null ? Double.parseDouble(fieldsMap.get("rating")) : 0.0)
                    .captureDateTime(fieldsMap.get("captureDateTime") == null ? null : Long.parseLong(fieldsMap.get("captureDateTime")))
                    .build();

            photos.add(photo);
        }

        return photos;
    }
}
