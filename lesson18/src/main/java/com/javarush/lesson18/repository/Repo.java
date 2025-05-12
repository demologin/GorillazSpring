package com.javarush.lesson18.repository;

import com.javarush.lesson18.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repo extends JpaRepository<User, Long> {


}
