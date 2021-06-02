package ru.geekbrains.springmarket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.springmarket.entities.User;
import ru.geekbrains.springmarket.repositories.UserRepository;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void initDbTest() {
        List<User> users = userRepository.findAll();
        Assertions.assertEquals(3, users.size());
        Assertions.assertEquals("admin", users.get(0).getUsername());
        Assertions.assertEquals("admin@gmail.com", users.get(0).getEmail());
    }

    @Test
    public void userRepositoryTest() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("testUser@gmail.com");
        user.setPassword("$2y$12$tB5vNOWyl.6PpuixxUt0Xe.82qgJLPGjygtjZ3Z1Gdx8RB6x5z.ZO");
        entityManager.persist(user);
        entityManager.flush();

        List<User> users = userRepository.findAll();

        Assertions.assertEquals(4, users.size());
        Assertions.assertEquals(user.getUsername(), users.get(3).getUsername());
        Assertions.assertEquals(user.getEmail(), users.get(3).getEmail());
        Assertions.assertEquals(user.getPassword(), users.get(3).getPassword());
    }
}
