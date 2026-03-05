package lv.nixx.cache.redis.object;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public Collection<Person> getAllPersons() {
        return personService.getAll();
    }

    @GetMapping("/{id}")
    public Map<String, Serializable> getPersonById(@PathVariable String id) {

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
