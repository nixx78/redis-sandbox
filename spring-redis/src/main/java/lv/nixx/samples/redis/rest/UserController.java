package lv.nixx.samples.redis.rest;

import lv.nixx.samples.redis.model.User;
import lv.nixx.samples.redis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping
    public void savePerson(@RequestBody Collection<User> roles) {
        repository.saveAll(roles);
    }

    @GetMapping
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{userName}")
    public User findByName(@PathVariable String userName) {
        return repository.findFirstByName(userName);
    }

    @DeleteMapping
    public void deleteAll() {
        repository.deleteAll();
    }

}
