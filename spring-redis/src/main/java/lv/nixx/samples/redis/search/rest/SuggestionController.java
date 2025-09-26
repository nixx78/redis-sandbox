package lv.nixx.samples.redis.search.rest;

import lv.nixx.samples.redis.search.SuggestionService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/suggestion")
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @PostMapping("/index")
    public void createIndexes() {
        suggestionService.reindex();
    }

    @GetMapping("/{word}")
    public Collection<String> getSuggestion(@PathVariable String word) {
        return suggestionService.getSuggestion(word);
    }

}
