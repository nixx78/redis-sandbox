package lv.nixx.samples.redis.repository;

import lv.nixx.samples.redis.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCrudRepository extends CrudRepository<User, String> {

    User findFirstByEmail(String email);

    User findFirstByName(String name);
}
