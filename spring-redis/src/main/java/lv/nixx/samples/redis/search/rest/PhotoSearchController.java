package lv.nixx.samples.redis.search.rest;

import lv.nixx.samples.redis.search.model.Photo;
import lv.nixx.samples.redis.search.model.PhotoSearchRequest;
import lv.nixx.samples.redis.search.service.PhotoSearchEngine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/photo")
public class PhotoSearchController {

    private final PhotoSearchEngine photoSearchEngine;

    public PhotoSearchController(PhotoSearchEngine photoSearchEngine) {
        this.photoSearchEngine = photoSearchEngine;
    }

    @PostMapping("/search")
    public Collection<Photo> search(@RequestBody PhotoSearchRequest request) {
        return photoSearchEngine.search(request);
    }
}
