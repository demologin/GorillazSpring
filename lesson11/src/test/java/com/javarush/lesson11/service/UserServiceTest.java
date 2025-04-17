package com.javarush.lesson11.service;

import com.javarush.lesson11.entity.Role;
import com.javarush.lesson11.entity.User;
import com.javarush.lesson11.repository.Repo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    public static final long ID = 1L;
    @Mock
    private Repo repo;

    @InjectMocks
    private UserService userService;

    private final User admin = User.builder()
            .id(ID)
            .login("Admin")
            .password("123")
            .role(Role.ADMIN)
            .build();

    private final User user = User.builder()
            .id(ID)
            .login("Alisa")
            .password("789")
            .role(Role.USER)
            .build();

    private final User newGuest = User.builder()
            .login("Bob")
            .password("456")
            .role(Role.GUEST)
            .build();

    private final User realGuest = User.builder()
            .id(3L)
            .login("Bob")
            .password("456")
            .role(Role.GUEST)
            .build();

//    @BeforeEach
//    void setUp() {
//        repo = Mockito.mock(Repo.class);
//        userService = new UserService(repo);
//    }

    @Test
    @DisplayName("When get by id then user found")
    void whenGetByIdThenUserFound() {
        when(repo.findById(ID)).thenReturn(Optional.of(admin));
        Optional<User> user = userService.get(ID);
        assertTrue(user.isPresent());
        verify(repo).findById(anyLong());
    }

    @Test
    @DisplayName("When get all users then size equals 3")
    void whenGetAllUsersThenSizeEquals3() {
        when(repo.findAll(any(Sort.class)))
                .thenReturn(List.of(admin, admin, user));
        List<User> all = userService.findAll();
        assertEquals(3, all.size());
        assertTrue(all.contains(admin));
        assertTrue(all.contains(user));
        verify(repo).findAll(any(Sort.class));
    }


    @Test
    void save() {
        when(repo.saveAndFlush(newGuest))
                .thenReturn(realGuest);
        User user = userService.save(newGuest);
        assertSame(realGuest, user);
        verify(repo).saveAndFlush(any(User.class));
    }
}