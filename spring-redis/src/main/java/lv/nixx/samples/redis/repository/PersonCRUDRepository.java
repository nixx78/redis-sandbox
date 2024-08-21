package lv.nixx.samples.redis.repository;

import lv.nixx.samples.redis.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonCRUDRepository extends CrudRepository<Person, Long> {
}
