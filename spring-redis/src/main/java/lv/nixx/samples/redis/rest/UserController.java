package lv.nixx.samples.redis.rest;

import lv.nixx.samples.redis.model.User;
import lv.nixx.samples.redis.repository.UserCrudRepository;
import lv.nixx.samples.redis.repository.UserPagingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserPagingRepository pagingRepository;
    private final UserCrudRepository crudRepository;

    public UserController(UserPagingRepository pagingRepository, UserCrudRepository crudRepository) {
        this.pagingRepository = pagingRepository;
        this.crudRepository = crudRepository;
    }

    @PostMapping
    public void savePerson(@RequestBody Collection<User> roles) {
        crudRepository.saveAll(roles);
    }

    @GetMapping
    public Iterable<User> findAll() {
        return crudRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findById(String id) {
        return crudRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User with id [" + id+ "] doesn't exists"));
    }

    @GetMapping("/findByUserName/{userName}")
    public User findByName(@PathVariable String userName) {
        return crudRepository.findFirstByName(userName);
    }

    @DeleteMapping
    public void deleteAll() {
        crudRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id) {
        crudRepository.deleteById(id);
    }


    @GetMapping("/byPage")
    public Map<String, Object> getPage(@RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "3") Integer size) {

        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Order.by("id")));
        Page<User> pagedResult = pagingRepository.findAll(paging);
        Collection<User> users = pagedResult.hasContent() ? pagedResult.getContent() : Collections.emptyList();

        return Map.of(
                "persons", users,
                "page", pagedResult.getNumber(),
                "pages", pagedResult.getTotalPages(),
                "total", pagedResult.getTotalElements()
        );
    }


}
