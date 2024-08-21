package lv.nixx.samples.redis.repository;

import lv.nixx.samples.redis.model.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonPagingRepository extends PagingAndSortingRepository<Person, Long> {
}
