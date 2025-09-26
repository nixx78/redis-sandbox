package lv.nixx.samples.redis.search.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lv.nixx.samples.redis.search.PhotoCrudRepository;
import lv.nixx.samples.redis.search.model.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.StringReader;
import java.util.Collection;

@RestController
@RequestMapping("/photo")
public class PhotoCrudController {

    private static final Logger log = LoggerFactory.getLogger(PhotoCrudController.class);

    private final PhotoCrudRepository photoCrudRepository;
    private final ObjectMapper objectMapper;

    public PhotoCrudController(PhotoCrudRepository photoCrudRepository, ObjectMapper objectMapper) {
        this.photoCrudRepository = photoCrudRepository;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public void addPhotos(@RequestBody Collection<Photo> photos) {
        photoCrudRepository.saveAll(photos);
    }

    @GetMapping
    public Iterable<Photo> findAll() {
        return photoCrudRepository.findAll();
    }

    @DeleteMapping
    public void deleteAll() {
        photoCrudRepository.deleteAll();
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPhotos(@RequestPart("files") MultipartFile file) {
        try {
            log.info("Start processing file [{}] size [{}]", file.getOriginalFilename(), file.getSize());

            StringReader jsonAsString = new StringReader(new String(file.getBytes()));

            Collection<Photo> photosToSave = objectMapper.readValue(
                    jsonAsString,
                    new TypeReference<>() {
                    }
            );

            photoCrudRepository.saveAll(photosToSave);

            return ResponseEntity.ok("Uploaded photo count: " + photosToSave.size());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error loading file: " + e.getMessage());
        }
    }


}
