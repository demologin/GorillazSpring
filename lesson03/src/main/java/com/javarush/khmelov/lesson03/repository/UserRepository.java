package com.javarush.khmelov.lesson03.repository;

import com.javarush.khmelov.lesson03.config.SessionCreator;
import com.javarush.khmelov.lesson03.model.User;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository {

    private final SessionCreator sessionCreator;

    public Optional<User> getUserById(Long id) {
        Session session = sessionCreator.getSession();
        Transaction tx = session.beginTransaction();
        try(session){
            User user = session.get(User.class, id);
            tx.commit();
            return Optional.of(user);
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        }
    }
}
