package lv.nixx.samples.redis.repository;

import lv.nixx.samples.redis.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserCrudRepositoryTest {

    @Autowired
    UserCrudRepository userCrudRepository;

    @Test
    void crudOperationsTest() {

        userCrudRepository.saveAll(List.of(new User()
                        .setId("UserId.1")
                        .setName("Name1")
                        .setEmail("name1@email.com"),
                new User()
                        .setId("UserId.2")
                        .setName("Name2")
                        .setEmail("name2@email.com")
        ));

        User user = userCrudRepository.findById("UserId.1").orElse(null);

        assertThat(user).usingRecursiveComparison().isEqualTo(
                new User()
                        .setId("UserId.1")
                        .setName("Name1")
                        .setEmail("name1@email.com")
        );

        User firstByEmail = userCrudRepository.findFirstByEmail("name2@email.com");
        assertThat(firstByEmail).usingRecursiveComparison().isEqualTo(
                new User()
                        .setId("UserId.2")
                        .setName("Name2")
                        .setEmail("name2@email.com")
        );
    }

}
