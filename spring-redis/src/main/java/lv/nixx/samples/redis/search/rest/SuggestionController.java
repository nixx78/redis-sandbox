package lv.nixx.samples.redis.search.rest;

import lv.nixx.samples.redis.search.model.IndexField;
import lv.nixx.samples.redis.search.service.SuggestionIndexService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/suggestion")
public class SuggestionController {

    private final SuggestionIndexService suggestionIndexService;

    public SuggestionController(SuggestionIndexService suggestionIndexService) {
        this.suggestionIndexService = suggestionIndexService;
    }

    @PostMapping("/index")
    public String createIndex(@RequestParam IndexField indexField) {
        long indexFieldCount = suggestionIndexService.createIndex(indexField);

        return "Index created for field [%s] size [%s]".formatted(indexField, indexFieldCount);
    }

    @DeleteMapping("/index")
    public void deleteIndex(@RequestParam IndexField indexField) {
        suggestionIndexService.deleteIndex(indexField);
    }

    @GetMapping
    public Collection<String> getSuggestion(@RequestParam IndexField indexField, @RequestParam String word) {
        return suggestionIndexService.getSuggestion(indexField, word);
    }

}
