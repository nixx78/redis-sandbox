package lv.nixx.samples.redis.rest;

import lv.nixx.samples.redis.model.Role;
import lv.nixx.samples.redis.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleRepository repository;

    @PostMapping
    public void savePerson(@RequestBody Collection<Role> roles) {
        repository.saveAll(roles);
    }

    @GetMapping("/{role}")
    public Role findRoleByName(@PathVariable String role) {
        return repository.findFirstByName(role);
    }

    @GetMapping
    public Iterable<Role> findAll() {
        return repository.findAll();
    }

    @DeleteMapping
    public void deleteAll() {
        repository.deleteAll();
    }

}
