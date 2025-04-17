package com.javarush.lesson11.service;

import com.javarush.annotation.ContextIT;
import com.javarush.lesson11.entity.Role;
import com.javarush.lesson11.entity.User;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AllArgsConstructor
@ContextIT
public class UserServiceIT {

    private final UserService userService;

    private final static Long ID = 1L;

    private final User newGuest = User.builder()
            .login("Bob")
            .password("456")
            .role(Role.GUEST)
            .build();


    @Test
    @DisplayName("When get by id then user found")
    void whenGetByIdThenUserFound() {
        Optional<User> user = userService.get(ID);
        assertTrue(user.isPresent());
    }

    @Test
    @DisplayName("When get all users then size equals 3")
    void whenGetAllUsersThenSizeEquals3() {
        List<User> all = userService.findAll();
        assertEquals(3, all.size());
    }

    @Test
    @DisplayName("when save user then get id in entity")
    void whenSaveUserThenGetIdInEntity() {
        User user = userService.save(newGuest);
        assertTrue(user.getId() > 0);
        assertEquals(newGuest.getLogin(),user.getLogin());
        assertEquals(newGuest.getRole(),user.getRole());
    }

}