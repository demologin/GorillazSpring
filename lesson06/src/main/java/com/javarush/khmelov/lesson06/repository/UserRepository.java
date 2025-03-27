package com.javarush.khmelov.lesson06.repository;


import com.javarush.khmelov.lesson06.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> getUserById(Long id);
}
