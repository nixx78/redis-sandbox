package lv.nixx.samples.redis.repository;

import lv.nixx.samples.redis.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPagingRepository extends PagingAndSortingRepository<User, String> {
}
