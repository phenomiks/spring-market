package ru.geekbrains.springmarket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.springmarket.entities.User;
import ru.geekbrains.springmarket.repositories.UserRepository;
import ru.geekbrains.springmarket.services.UserService;

import java.util.Optional;

@SpringBootTest(classes = UserService.class)
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findByUsernameTest() {
        User user = new User();
        user.setId(5L);
        user.setUsername("testUser");
        user.setEmail("testUser@gmail.com");
        user.setPassword("$2y$12$tB5vNOWyl.6PpuixxUt0Xe.82qgJLPGjygtjZ3Z1Gdx8RB6x5z.ZO");

        Mockito
                .doReturn(Optional.of(user))
                .when(userRepository)
                .findByUsername(user.getUsername());

        User u = userService.findByUsername(user.getUsername()).get();
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(ArgumentMatchers.eq(user.getUsername()));
        Assertions.assertEquals(user.getUsername(), u.getUsername());
    }
}
