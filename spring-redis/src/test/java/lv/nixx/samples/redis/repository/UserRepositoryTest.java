package lv.nixx.samples.redis.repository;

import lv.nixx.samples.redis.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void crudOperationsTest() {

        userRepository.saveAll(List.of(new User()
                        .setId("UserId.1")
                        .setName("Name1")
                        .setEmail("name1@email.com"),
                new User()
                        .setId("UserId.2")
                        .setName("Name2")
                        .setEmail("name2@email.com")
        ));

        User user = userRepository.findById("UserId.1").orElse(null);

        assertThat(user).usingRecursiveComparison().isEqualTo(
                new User()
                        .setId("UserId.1")
                        .setName("Name1")
                        .setEmail("name1@email.com")
        );

        User firstByEmail = userRepository.findFirstByEmail("name2@email.com");
        assertThat(firstByEmail).usingRecursiveComparison().isEqualTo(
                new User()
                        .setId("UserId.2")
                        .setName("Name2")
                        .setEmail("name2@email.com")
        );
    }

}
