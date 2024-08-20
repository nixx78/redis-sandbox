package lv.nixx.samples.redis.rest;

import lv.nixx.samples.redis.model.Person;
import lv.nixx.samples.redis.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @PostMapping
    public void savePerson(@RequestBody Collection<Person> person) {
        repository.saveAll(person);
    }

    @GetMapping
    public Iterable<Person> findAll() {
        return repository.findAll();
    }

    @DeleteMapping
    public void deleteAll() {
        repository.deleteAll();
    }

}
