package lv.nixx.samples.redis.repository;

import lv.nixx.samples.redis.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findFirstByName(String role);
}
