package lv.nixx.samples.redis.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lv.nixx.samples.redis.search.PhotoCrudRepository;
import lv.nixx.samples.redis.search.model.IndexField;
import lv.nixx.samples.redis.search.model.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class PhotoUploadService {

    private static final Logger log = LoggerFactory.getLogger(PhotoUploadService.class);

    private final PhotoCrudRepository photoCrudRepository;
    private final ObjectMapper objectMapper;
    private final SuggestionIndexService suggestionIndexService;

    public PhotoUploadService(PhotoCrudRepository photoCrudRepository,
                              ObjectMapper objectMapper,
                              SuggestionIndexService suggestionIndexService
    ) {
        this.photoCrudRepository = photoCrudRepository;
        this.objectMapper = objectMapper;
        this.suggestionIndexService = suggestionIndexService;
    }

    public String uploadPhotos(MultipartFile file) throws IOException {

        log.info("Start processing file [{}] size [{}]", file.getOriginalFilename(), file.getSize());

        StringReader jsonAsString = new StringReader(new String(file.getBytes()));

        Collection<Photo> photosToSave = objectMapper.readValue(
                jsonAsString,
                new TypeReference<>() {
                }
        );

        photoCrudRepository.saveAll(photosToSave);

        Collection<String> placesToIndex = photosToSave.stream().map(Photo::place).collect(Collectors.toSet());
        suggestionIndexService.createIndex(IndexField.place, placesToIndex);

        return "Uploaded photo count: " + photosToSave.size();
    }

}
