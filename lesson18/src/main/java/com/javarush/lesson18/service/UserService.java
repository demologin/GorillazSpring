package com.javarush.lesson18.service;

import com.javarush.lesson18.dto.UserTo;
import com.javarush.lesson18.entity.User;
import com.javarush.lesson18.map.Mapper;
import com.javarush.lesson18.repository.Repo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final Repo repo;

    public Optional<UserTo> get(Long id) {
        return repo.findById(id).map(Mapper::map);
    }

    public List<UserTo> findAll() {
        Sort sort = Sort.sort(User.class).by(User::getId);
        return repo.findAll(sort)
                .stream()
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserTo save(UserTo userTo) {
        String rawPassword = userTo.getPassword();
        String encode = passwordEncoder.encode(rawPassword);
        User user = Mapper.map(userTo);
        user.setPassword(encode);
        return Mapper.map(repo.saveAndFlush(user));
    }

    @Transactional
    public void delete(UserTo userTo) {
        repo.deleteById(userTo.getId());
    }

    @Transactional
    public void deleteById(Long id) {
        repo.deleteById(id);
    }


}
