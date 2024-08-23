package lv.nixx.samples.redis;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sandbox")
public class ControllerForSlowService {

    private final MySlowService mySlowService;

    public ControllerForSlowService(MySlowService mySlowService) {
        this.mySlowService = mySlowService;
    }

    @GetMapping("/{id}")
    public Map<String, Object> callSlowService(@PathVariable String id) {

        long st = System.currentTimeMillis();
        String value = mySlowService.getValueById(id);

        return Map.of(
                "value", value,
                "processingTime", (System.currentTimeMillis() - st)
        );
    }

    @PostMapping
    public String add(@RequestBody Request request) {
        return mySlowService.add(request.getId(), request.getValue());
    }

    @PutMapping
    public String updateEntity(@RequestBody Request request) {
        return mySlowService.updateEntity(request.getId(), request.getValue());
    }

    @DeleteMapping("{id}")
    public String deleteEntity(@PathVariable String id) {
        return mySlowService.delete(id);
    }

    @DeleteMapping("clearCache")
    public void clearCache() {
        mySlowService.clearCache();
    }

}
