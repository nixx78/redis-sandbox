package lv.nixx.samples.redis.search.service;

import lv.nixx.samples.redis.search.model.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoCrudRepository extends CrudRepository<Photo, String> {
}
