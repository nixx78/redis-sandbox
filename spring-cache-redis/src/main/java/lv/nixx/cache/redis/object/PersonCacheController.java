package lv.nixx.cache.redis.object;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PersonCacheController {

    private final PersonCacheControlService personCacheControlService;

    @GetMapping("/cache/clear")
    public void clear() {
        personCacheControlService.clear();
    }

    @GetMapping("/cache/loadDataDirectlyToCache")
    public void loadData() {
        personCacheControlService.loadData();
    }

}
