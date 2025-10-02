package lv.nixx.samples.redis.search.rest;

import lv.nixx.samples.redis.search.PhotoCrudRepository;
import lv.nixx.samples.redis.search.model.Photo;
import lv.nixx.samples.redis.search.service.PhotoUploadService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/photo")
public class PhotoCrudController {

    private final PhotoCrudRepository photoCrudRepository;
    private final PhotoUploadService photoUploadService;

    public PhotoCrudController(PhotoCrudRepository photoCrudRepository, PhotoUploadService photoUploadService) {
        this.photoCrudRepository = photoCrudRepository;
        this.photoUploadService = photoUploadService;
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
        // Remove all suggestion index there
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPhotos(@RequestPart("files") MultipartFile file) {
        try {

            return ResponseEntity.ok(photoUploadService.uploadPhotos(file));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error loading file: " + e.getMessage());
        }
    }


}
