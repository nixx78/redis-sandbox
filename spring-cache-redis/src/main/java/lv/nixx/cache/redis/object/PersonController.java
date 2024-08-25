package lv.nixx.cache.redis.object;

import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public Map<String, Serializable> callSlowService(@PathVariable String id) {

        long st = System.currentTimeMillis();
        Person person = personService.getValueById(id);

        return Map.of(
                "value", person,
                "processingTime", (System.currentTimeMillis() - st)
        );
    }

    @PostMapping
    public Person add(@RequestBody Person person) {
        return personService.add(person);
    }

    @PutMapping
    public Person updateEntity(@RequestBody Person person) {
        return personService.updateEntity(person);
    }

    @DeleteMapping("{id}")
    public Person deleteEntity(@PathVariable String id) {
        return personService.delete(id);
    }

    @DeleteMapping("clearCache")
    public void clearCache() {
        personService.clearCache();
    }

}
