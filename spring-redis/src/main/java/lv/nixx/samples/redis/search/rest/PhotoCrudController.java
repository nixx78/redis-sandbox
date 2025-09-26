package lv.nixx.samples.redis.search.rest;

import lv.nixx.samples.redis.search.PhotoCrudRepository;
import lv.nixx.samples.redis.search.model.Photo;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/photo")
public class PhotoCrudController {

    private final PhotoCrudRepository photoCrudRepository;

    public PhotoCrudController(PhotoCrudRepository photoCrudRepository) {
        this.photoCrudRepository = photoCrudRepository;
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

}
