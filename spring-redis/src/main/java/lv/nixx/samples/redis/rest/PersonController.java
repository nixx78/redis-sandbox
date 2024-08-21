package lv.nixx.samples.redis.rest;

import lv.nixx.samples.redis.model.Person;
import lv.nixx.samples.redis.repository.PersonCRUDRepository;
import lv.nixx.samples.redis.repository.PersonPagingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.LongStream;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonCRUDRepository repository;
    private final PersonPagingRepository pagingRepository;

    public PersonController(PersonCRUDRepository repository, PersonPagingRepository pagingRepository) {
        this.repository = repository;
        this.pagingRepository = pagingRepository;
    }

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

    @GetMapping("/byPage")
    public Map<String, Object> getPage(@RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size) {

        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Order.by("personId")));
        Page<Person> pagedResult = pagingRepository.findAll(paging);
        Collection<Person> persons = pagedResult.hasContent() ? pagedResult.getContent() : Collections.emptyList();

        return Map.of(
                "persons", persons,
                "page", pagedResult.getNumber(),
                "pages", pagedResult.getTotalPages(),
                "total", pagedResult.getTotalElements()
        );
    }

    @GetMapping("/uploadBulk")
    public void uploadBulk() {
        repository.deleteAll();

        repository.saveAll(LongStream.range(0, 100)
                .mapToObj(i -> new Person(i, "Name_" + i))
                .toList()
        );
    }


}
